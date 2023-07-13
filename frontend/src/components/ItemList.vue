<script lang="ts" setup>

defineProps(['items']);
defineEmits(['delete', 'setDoneStatus'])
</script>

<template>
  <div id="item-list" data-cy="item-list">
    <div v-for="item in items"
         :key="item.id" :class="{ 'done': item.isDone, 'undone': !item.isDone}"
         :data-cy="'item_' + item.id"
         class="item-box row"
    >
      <p class="col align-self-end">{{ item.message }}</p>
      <img alt="" class="col-2 align-self-center delete"
           src="@/images/trashcan.png"
           @click="$emit('delete', item)"/>
      <img v-if="item.isDone" id="checkbox" alt=""
           class="col-2 align-self-center"
           src="@/images/circle_checked_white.png"
           @click="$emit('setDoneStatus', item._links.setToUndone.href)"
      />
      <img v-else id="checkbox" alt="" class="col-2 align-self-center"
           src="@/images/circle_empty_white.png"
           @click="$emit('setDoneStatus', item._links.setToDone.href)"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
#item-list {
  margin-top: 2em;
  display: flex;
  flex-direction: column;
  row-gap: 0.5em;
}

.item-box {
  &:hover .delete {
    opacity: 0.6;
  }

  img {
    height: 60px;
    width: auto;
    opacity: 0.6;

    &.delete {
      opacity: 0;
    }

    &:hover {
      opacity: 1 !important;
      cursor: pointer;
    }
  }
}
</style>