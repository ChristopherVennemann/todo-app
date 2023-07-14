<script lang="ts" setup>
import {Ref, ref} from "vue";

const emit = defineEmits(['selectedCategory']);

const currentTab: Ref<string> = ref('all');

function setCurrentTabTo(tab: string) {
  currentTab.value = tab;
  emit('selectedCategory', tab);
}
</script>

<template>
  <div class="categories-tab">
    <span :class="{ 'active': currentTab==='all'}"
          class="category"
          data-cy="category-all"
          @click="setCurrentTabTo('all')"
    > all </span>
    <span> | </span>
    <span :class="{ 'active': currentTab==='unfinished'}"
          class="category"
          data-cy="category-unfinished"
          @click="setCurrentTabTo('unfinished')"
    > unfinished </span>
    <span> | </span>
    <span :class="{ 'active': currentTab==='finished'}"
          class="category"
          data-cy="category-finished"
          @click="setCurrentTabTo('finished')"
    > finished </span>
  </div>
</template>

<style lang="scss" scoped>
@import "src/assets/fontsAndColors.scss";

.categories-tab {
  color: $primary-color;
  font: $font-categories;

  span {
    opacity: 0.4;

    &.category {
      &:hover {
        cursor: pointer;
        opacity: 0.6;
      }
    }

    &.active {
      opacity: 0.6;
    }
  }
}
</style>