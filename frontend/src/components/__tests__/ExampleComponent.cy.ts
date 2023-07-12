import ExampleComponent from "@/components/ExampleComponent.vue";

describe('ExampleComponent', () => {
    it('finds the button', () => {
        cy.mount(ExampleComponent);
        cy.get('button');
    })
})