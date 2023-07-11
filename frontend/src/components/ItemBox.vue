<template>
  <div class="item-box row">
    <slot/>
  </div>
</template>

<style lang="scss" scoped>
@import "src/assets/fontsAndColors";

.item-box {
  height: 3em;
  padding: 0 1em;
  background-color: rgba($primary-color, 0.2);
  border-radius: 0.5em;
  font: $font-items;

  &.done {
    opacity: 0.3;
  }

  &:hover {
    box-shadow: 1px 1px rgba($shadow-color, 0.5);
    background-color: rgba($primary-color, 0.4);
  }
}
</style>