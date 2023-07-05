// @ts-ignore
import App from "../../App.vue";
import collectionModel from "@/types/CollectionModel";

const validRequestBody = {message: "item 1"};

const collectionModelEmpty: collectionModel = {
    _links: {
        self: {
            href: "http://localemptyCollectionModelst:8082/items"
        },
        post: {
            href: "http://localhost:8082/items"
        }
    }
}

const collectionModelOneItem: collectionModel = {
    _embedded: {
        itemResponseList: [
            {
                id: 1,
                message: "test",
                done: false,
                _links: {
                    collection: {
                        href: "http://localhost:8082/items"
                    },
                    delete: {
                        href: "http://localhost:8082/items/1"
                    }
                }
            }
        ]
    },
    _links: {
        self: {
            href: "http://localemptyCollectionModelst:8082/items"
        },
        post: {
            href: "http://localhost:8082/items"
        }
    }
}

describe('<App />', () => {
    it('renders', () => {
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelEmpty}).as('getAll');
        cy.mount(App);
    })

    it('has no initial items', () => {
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelEmpty}).as('getAll');

        cy.mount(App);
        cy.get('[data-cy=item]').should('have.length', 0);
    })

    it('serializes input value to post request body', () => {
        const expectedMessage: String = 'item 1';
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelEmpty}).as('getAll');
        cy.intercept('POST', '/items', {}).as('postItem');

        cy.mount(App);
        cy.get('#new-message').type(expectedMessage);
        cy.get('#plus').click();

        cy.wait('@postItem').should((result) => {
            // @ts-ignore
            expect(result.request.body.message).to.equal(expectedMessage);
        })
    })

    it('appends post response body to list', () => {
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelEmpty}).as('getAll');
        cy.intercept('POST', '/items', {statusCode: 201, body: validRequestBody});

        cy.mount(App);
        cy.get('#plus').click();

        cy.get('[data-cy=item]').should(($items) => {
            // @ts-ignore
            expect($items).to.have.length(1);
            // @ts-ignore
            expect($items.eq(0)).to.contain(validRequestBody.message);
        })
    })

    it('clears new-message field on successful creation', () => {
        const expectedEmptyString: String = '';
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelEmpty}).as('getAll');
        cy.intercept('POST', '/items', {statusCode: 201, body: validRequestBody});

        cy.mount(App);
        cy.get('#new-message').type('bar');
        cy.get('#plus').click();

        cy.get('#new-message').should('have.value', expectedEmptyString);
    })

    it('deletes the item when delete button is clicked', () => {
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelOneItem});
        cy.intercept('DELETE', '/items/**', {statusCode: 204}).as('deleteRequest');

        cy.mount(App);
        cy.get('#delete').click();

        cy.get('[data-cy=item]').should('have.length', 0);
    })

    it('sends delete request that contains the item id', () => {
        const expectedDeleteUrl: string = collectionModelOneItem._embedded.itemResponseList[0]._links.delete.href;
        cy.intercept('GET', '/items', {statusCode: 200, body: collectionModelOneItem});
        cy.intercept('DELETE', '/items/**', {statusCode: 204}).as('deleteRequest');

        cy.mount(App);
        cy.get('#delete').click();

        cy.wait('@deleteRequest').should(deleteRequest => {
            // @ts-ignore
            expect(deleteRequest.request.url).to.equal(expectedDeleteUrl);
        })
    })
})
