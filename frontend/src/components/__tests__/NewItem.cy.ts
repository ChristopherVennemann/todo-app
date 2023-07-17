import NewItem from "@/components/NewItem.vue";

describe('NewItem', () => {
    it('renders', () => {
        cy.mount(NewItem);
    })

    it('emits the right event when plus-sign is clicked', () => {
        const expectedString = 'test string';

        cy.mount(NewItem);
        cy.get('[data-cy="textInput"]').type(expectedString)
        cy.get('[data-cy="addItemButton"]')
            .click()
            .vue()
            .then((wrapper) => {
                // @ts-ignore
                expect(wrapper.emitted('newMessage')).to.have.length(1);
                // @ts-ignore
                expect(wrapper.emitted('newMessage')[0][0]).to.equal(expectedString);
            });
    });

    it('submits new item when input has focus and enter is pressed', () => {
        const expectedString = 'test string';

        cy.mount(NewItem);
        cy.get('[data-cy="textInput"]')
            .type(expectedString + '{enter}')
            .vue()
            .then((wrapper) => {
                // @ts-ignore
                expect(wrapper.emitted('newMessage')).to.have.length(1);
                // @ts-ignore
                expect(wrapper.emitted('newMessage')[0][0]).to.equal(expectedString);
            });
    })

    it('returns focus in the input element after a the plus button is clicked', () => {
        cy.mount(NewItem);
        cy.get('[data-cy="textInput"]').type('test');
        cy.get('[data-cy="addItemButton"]')
            .click();

        cy.get('[data-cy="textInput"]').should('have.focus');
    })
})