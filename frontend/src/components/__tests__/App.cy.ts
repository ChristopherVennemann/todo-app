// @ts-ignore
import App from "../../App.vue";

describe('<App />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-vue
    cy.mount(App)
  })

  it('has four items', () => {
    cy.mount(App)
    cy.get('[data-cy=item]').should('have.length', 4)
  })
})