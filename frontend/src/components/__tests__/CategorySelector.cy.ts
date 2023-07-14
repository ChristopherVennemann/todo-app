import CategorySelector from "@/components/CategorySelector.vue";

describe('CategorySelector', () => {
    it('renders', () => {
        cy.mount(CategorySelector);
    })

    it('has span that can become active by clicking', () => {
        cy.mount(CategorySelector);

        cy.get('[data-cy="category-all"]')
            .should('have.class', 'active');
        cy.get('[data-cy="category-unfinished"]')
            .should('not.have.class', 'active');
        cy.get('[data-cy="category-finished"]')
            .should('not.have.class', 'active');

        cy.get('[data-cy="category-unfinished"]').click()
            .should('have.class', 'active');
        cy.get('[data-cy="category-all"]')
            .should('not.have.class', 'active');
        cy.get('[data-cy="category-finished"]')
            .should('not.have.class', 'active');

        cy.get('[data-cy="category-finished"]').click()
            .should('have.class', 'active');
        cy.get('[data-cy="category-all"]')
            .should('not.have.class', 'active');
        cy.get('[data-cy="category-unfinished"]')
            .should('not.have.class', 'active');
    })

    it('emits a correct event when a category is clicked', () => {
        cy.mount(CategorySelector);

        cy.get('[data-cy="category-finished"]')
            .click()
            .vue()
            .then((wrapper) => {
                // @ts-ignore
                expect(wrapper.emitted('selectedCategory')).to.have.length(1);
                // @ts-ignore
                expect(wrapper.emitted('selectedCategory')[0][0]).to.equal('finished');
            });

        cy.get('[data-cy="category-unfinished"]')
            .click()
            .vue()
            .then((wrapper) => {
                // @ts-ignore
                expect(wrapper.emitted('selectedCategory')).to.have.length(2);
                // @ts-ignore
                expect(wrapper.emitted('selectedCategory')[1][0]).to.equal('unfinished');
            });

        cy.get('[data-cy="category-all"]')
            .click()
            .vue()
            .then((wrapper) => {
                // @ts-ignore
                expect(wrapper.emitted('selectedCategory')).to.have.length(3);
                // @ts-ignore
                expect(wrapper.emitted('selectedCategory')[2][0]).to.equal('all');
            });
    })
})