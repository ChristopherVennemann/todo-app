<template>
  <div class="item-box row">
    <slot/>
  </div>
</template>

<style lang="scss" scoped>

$primary-color: #FFFFFF;
$shadow-color: #000000;
$font-stack: MyPoppins, Calibri, sans-serif;
$font-items: 1.6rem $font-stack;
$font-title: bold 7rem $font-stack;

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