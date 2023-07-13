// @ts-ignore
import App from "../../App.vue";
import {CollectionModel, Item} from "@/types/CollectionModelTypes";
import {TestData} from "../../../test/TestData";

describe('<App />', () => {
    it('renders', () => {
        cy.mount(App);
    })

    it('gets hateoas model from hateoas endpoint', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            status: 200,
            body: hateoas
        }).as('getHateoas');

        const emptyModel: CollectionModel = TestData.generateItemModel();
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            status: 200,
            body: emptyModel
        }).as('getItems');

        cy.mount(App);

        cy.wait('@getHateoas').should((result) => {
            // @ts-ignore
            expect(result.request.url).to.equal(hateoas._links.self.href);
        });
        cy.wait('@getItems').should((result) => {
            // @ts-ignore
            expect(result.request.url).to.equal(emptyModel._links.self.href);
        })

    })

    it('serializes input value to post request body', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const emptyModel: CollectionModel = TestData.generateItemModel();
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: emptyModel
        });

        const expectedMessage: String = 'item 1';
        cy.intercept('POST', emptyModel._links.post.href, {}).as('postItem');

        cy.mount(App);
        cy.get('#new-message').type(expectedMessage);
        cy.get('#plus').click();

        cy.wait('@postItem').should((result) => {
            // @ts-ignore
            expect(result.request.body.message).to.equal(expectedMessage);
        })
    })

    it('appends post response body to list', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const emptyModel: CollectionModel = TestData.generateItemModel();
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: emptyModel
        });

        const undoneItem = TestData.generateItem(1, "test", false);
        cy.intercept('POST', emptyModel._links.post.href, {
            statusCode: 201,
            body: undoneItem
        });

        cy.mount(App);
        cy.get('[data-cy="textInput"]').type(undoneItem.message);
        cy.get('[data-cy="addItemButton"]').click();

        cy.get('[data-cy="item_1"]').should((item) => {
            // @ts-ignore
            expect(item).to.contain(undoneItem.message);
        });
    })

    it('clears new-message field on successful creation', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const emptyModel = TestData.generateItemModel();
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: emptyModel
        }).as('getAll');

        const undoneItem1 = TestData.generateItem(1, "test", false)
        const expectedEmptyString: String = '';
        cy.intercept('POST', emptyModel._links.post.href, {
            statusCode: 201,
            body: undoneItem1
        });

        cy.mount(App);
        cy.get('#new-message').type('bar');
        cy.get('#plus').click();

        cy.get('#new-message').should('have.value', expectedEmptyString);
    })

    it('deletes the item when delete button is clicked', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const item: Item = TestData.generateItem(1, "test", false);
        const model: CollectionModel = TestData.generateItemModel([item])
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: model
        });
        cy.intercept('DELETE', item._links.delete.href, {statusCode: 204}).as('deleteRequest');

        cy.mount(App);
        cy.get('.delete').click();

        cy.get('[data-cy=item_1]').should('have.length', 0);
    })

    it('changes item status when done-checkbox is clicked', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const undoneItem: Item = TestData.generateItem(1, "test", false);
        const model = TestData.generateItemModel([undoneItem])
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: model
        });

        const doneItem: Item = TestData.generateItem(1, "test", true);
        const setToDoneUrl = undoneItem._links.setToDone.href;
        const setToUndoneUrl = doneItem._links.setToUndone.href;
        cy.intercept('PUT', setToDoneUrl, {
            statusCode: 200,
            body: doneItem
        }).as('doneRequest');
        cy.intercept('PUT', setToUndoneUrl, {
            statusCode: 200,
            body: undoneItem
        }).as('undoneRequest');

        cy.mount(App);

        cy.get('#checkbox').click();
        cy.wait('@doneRequest').should(result => {
            // @ts-ignore
            expect(result.request.url).to.equal(setToDoneUrl);
        })

        cy.get('#checkbox').click();
        cy.wait('@undoneRequest').should(result => {
            // @ts-ignore
            expect(result.request.url).to.equal(setToUndoneUrl);
        })
    })

    it('updates item list after done status change', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const undoneItem: Item = TestData.generateItem(1, "test", false);
        const model: CollectionModel = TestData.generateItemModel([undoneItem])
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: model
        });

        const doneItem: Item = TestData.generateItem(1, "test", true);
        const setDoneUrl: string = undoneItem._links.setToDone.href;
        const setUndoneUrl: string = doneItem._links.setToUndone.href;
        cy.intercept('PUT', setDoneUrl, {statusCode: 200, body: doneItem});
        cy.intercept('PUT', setUndoneUrl, {statusCode: 200, body: undoneItem});

        cy.mount(App);
        cy.get('#checkbox').click()
        cy.get('[data-cy=item_1]').should('have.class', 'done');

        cy.get('#checkbox').click()
        cy.get('[data-cy=item_1]').should('have.class', 'undone');
    })

    it('sorts done items to the end of the list', () => {
        const hateoas: CollectionModel = TestData.generateHateoasModel();
        cy.intercept('GET', hateoas._links.self.href, {
            statusCode: 200,
            body: hateoas
        });

        const undoneItem1: Item = TestData.generateItem(1, "test", false);
        const undoneItem2: Item = TestData.generateItem(2, "test", false);
        const model: CollectionModel = TestData.generateItemModel([undoneItem1, undoneItem2]);
        cy.intercept('GET', hateoas._links.itemCollection.href, {
            statusCode: 200,
            body: model
        });

        const doneItem1: Item = TestData.generateItem(1, "test", true);
        const setDoneUrl: string = undoneItem1._links.setToDone.href;
        const setUndoneUrl: string = doneItem1._links.setToUndone.href;
        cy.intercept('PUT', setDoneUrl, {StatusCode: 200, body: doneItem1});
        cy.intercept('PUT', setUndoneUrl, {StatusCode: 200, body: undoneItem1});

        cy.mount(App);
        cy.get('[data-cy=item_1]').find('#checkbox').click();

        cy.get('[data-cy^=item_]').eq(1)
            .should('have.attr', 'data-cy')
            .and('equals', 'item_1');
    })
})
