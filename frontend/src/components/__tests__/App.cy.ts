// @ts-ignore
import App from "../../App.vue";
import Item from "@/types/Item";

describe('<App />', () => {
  it('renders', () => {
    cy.intercept('GET', '/items', { statusCode: 200, body: [] }).as('getAll')

    cy.mount(App)
  })

  it('has no initial items', () => {
    cy.intercept('GET', '/items', { statusCode: 200, body: [] }).as('getAll')

    cy.mount(App)
    cy.get('[data-cy=item]').should('have.length', 0)
  })

  it('serializes input value to post request body', () => {
    const expectedMessage: String = 'item 1'
    cy.intercept('GET', '/items', { statusCode: 200, body: [] }).as('getAll')
    cy.intercept('POST', '/items', {}).as('postItem')

    cy.mount(App)
    cy.get('#new-message').type(expectedMessage)
    cy.get('#plus').click()

    cy.wait('@postItem').should((result) => {
      expect(result.request.body.message).to.equal(expectedMessage)
    })
  })

  it('appends post response body to list', () => {
    const expectedItem: Item = { message: 'test', id: 1 }
    cy.intercept('GET', '/items', { statusCode: 200, body: [] }).as('getAll')
    cy.intercept('POST', '/items', { statusCode: 201, body: expectedItem })

    cy.mount(App)
    cy.get('#plus').click()

    cy.get('[data-cy=item]').should(($items) => {
      expect($items).to.have.length(1)
      expect($items.eq(0)).to.contain(expectedItem.message)
    })
  })

  it('clears new-message field on successful creation', () => {
    const expectedEmptyString: String = ''
    const validItem: Item = { message: 'foo', id: 1 }
    cy.intercept('GET', '/items', { statusCode: 200, body: [] }).as('getAll')
    cy.intercept('POST', '/items', { statusCode: 201, body: validItem })

    cy.mount(App)
    cy.get('#new-message').type('bar')
    cy.get('#plus').click()

    cy.get('#new-message').should('have.value', expectedEmptyString)
  })
})