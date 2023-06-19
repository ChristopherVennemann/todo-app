// @ts-ignore
import App from "../../App.vue";

describe('<App />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-vue
    cy.mount(App)
  })

  it('has no initial items', () => {
    cy.mount(App)
    cy.get('[data-cy=item]').should('have.length', 0)
  })

  it('adds new item', () => {
    cy.mount(App)
    cy.intercept('POST', '/items', {
      statusCode: 201,
      body: {
        message: 'test'
      },
    })

    cy.get('input').type('testing')
    cy.get('button').click()

    cy.get('[data-cy=item]').should(($items) => {
      expect($items).to.have.length(1)
      expect($items.eq(0)).to.contain('test')
    })
  })
})