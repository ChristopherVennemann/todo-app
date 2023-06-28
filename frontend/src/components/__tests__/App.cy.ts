// @ts-ignore
import App from "../../App.vue";
import Item from "@/types/Item";

describe('<App />', () => {
    it('renders', () => {
        cy.intercept('GET', '/items', {statusCode: 200, body: []}).as('getAll')

        cy.mount(App)
    })

    it('has no initial items', () => {
        cy.intercept('GET', '/items', {statusCode: 200, body: []}).as('getAll')

        cy.mount(App)
        cy.get('[data-cy=item]').should('have.length', 0)
    })

    it('serializes input value to post request body', () => {
        const expectedMessage: String = 'item 1'
        cy.intercept('GET', '/items', {statusCode: 200, body: []}).as('getAll')
        cy.intercept('POST', '/items', {}).as('postItem')

        cy.mount(App)
        cy.get('#new-message').type(expectedMessage)
        cy.get('#plus').click()

        cy.wait('@postItem').should((result) => {
            // @ts-ignore
            expect(result.request.body.message).to.equal(expectedMessage)
        })
    })

    it('appends post response body to list', () => {
        const expectedItem: Item = {message: 'test', id: 1}
        cy.intercept('GET', '/items', {statusCode: 200, body: []}).as('getAll')
        cy.intercept('POST', '/items', {statusCode: 201, body: expectedItem})

        cy.mount(App)
        cy.get('#plus').click()

        cy.get('[data-cy=item]').should(($items) => {
            // @ts-ignore
            expect($items).to.have.length(1)
            // @ts-ignore
            expect($items.eq(0)).to.contain(expectedItem.message)
        })
    })

    it('clears new-message field on successful creation', () => {
        const expectedEmptyString: String = ''
        const validItem: Item = {message: 'foo', id: 1}
        cy.intercept('GET', '/items', {statusCode: 200, body: []}).as('getAll')
        cy.intercept('POST', '/items', {statusCode: 201, body: validItem})

        cy.mount(App)
        cy.get('#new-message').type('bar')
        cy.get('#plus').click()

        cy.get('#new-message').should('have.value', expectedEmptyString)
    })

    it('deletes the item when delete button is clicked', () => {
        const existingItem: Item = {id: 1, message: "test"}
        cy.intercept('GET', '/items', {statusCode: 200, body: [existingItem]})
        cy.intercept('DELETE', '/items/**', {statusCode: 204}).as('deleteRequest');

        cy.mount(App);
        cy.get('#delete').click();

        cy.get('[data-cy=item]').should('have.length', 0);
    })

    it('sends delete request that contains the item id', () => {
        const existingItem: Item = {id: 1, message: "test"}
        const expectedUrl: RegExp = new RegExp(`^.*/items/${existingItem.id}$`)
        cy.intercept('GET', '/items', {statusCode: 200, body: [existingItem]})
        cy.intercept('DELETE', '/items/**', {statusCode: 204}).as('deleteRequest');

        cy.mount(App);
        cy.get('#delete').click();

        cy.wait('@deleteRequest').should(deleteRequest => {
            // @ts-ignore
            expect(deleteRequest.request.url).to.match(expectedUrl);
        })
    })
})
