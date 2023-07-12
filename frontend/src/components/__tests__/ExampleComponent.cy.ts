import ExampleComponent from "@/components/ExampleComponent.vue";

describe('ExampleComponent', () => {
    it('finds the button', () => {
        cy.mount(ExampleComponent);
        cy.get('button');
    });

    it('emits an event', () => {
        cy.mount(ExampleComponent);

        cy.get('button')
            .click()
            .vue()
            .then((wrapper) => {
                expect(wrapper.emitted('greeting')).to.have.length(1)
            });
    });
});
