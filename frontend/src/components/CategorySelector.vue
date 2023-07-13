<script lang="ts" setup>
import {Ref, ref} from "vue";

const emit = defineEmits(['selectedCategory']);

const currentTab: Ref<string> = ref('unfinished');

function setCurrentTabTo(tab: string) {
  currentTab.value = tab;
  emit('selectedCategory', tab);
}
</script>

<template>
  <div class="categories-tab">
    <span :class="{ 'active': currentTab==='all'}"

          @click="setCurrentTabTo('all')"
    > All </span>
    |
    <span :class="{ 'active': currentTab==='unfinished'}"
          @click="setCurrentTabTo('unfinished')"
    > Unfinished </span>
    |
    <span :class="{ 'active': currentTab==='finished'}"
          @click="setCurrentTabTo('finished')"
    > Finished </span>
  </div>
</template>

<style lang="scss" scoped>
@import "src/assets/fontsAndColors.scss";

.categories-tab {
  color: $primary-color;
  font: $font-categories;

  span {
    opacity: 0.6;

    &:hover {
      cursor: pointer;
      opacity: 1;
    }

    &.active {
      opacity: 1;
      text-decoration-line: underline;
    }
  }
}
</style>