<script lang="ts" setup>
import {Ref, ref, watch} from "vue";

let emit = defineEmits(['newMessage']);

let message: Ref<string> = ref('');
let isValidMessage: boolean = false;
let wasValidAttempt: boolean = true;

watch(message, () => {
  isValidMessage = (message.value.trim().length !== 0);
  setErrorMessage()
})

function sendMessage() {
  if (isValidMessage) {
    emit('newMessage', message.value);
    message.value = '';
    wasValidAttempt = true;
  } else {
    wasValidAttempt = false;
  }
  setErrorMessage()
}

function setErrorMessage() {
  if (!isValidMessage && !wasValidAttempt) {
    errorMessage.value = 'Enter some text to create a new To-Do!';
  } else {
    errorMessage.value = '';
  }
}

const errorMessage: Ref<string> = ref(' ');

</script>

<template>
  <div id="new-item" class="item-box row">
    <input id="new-message" v-model="message" class="col"
           data-cy="textInput" placeholder=". . . add new item"
           type="text"
    />
    <img id="plus" alt="" class="col-3 align-self-center"
         data-cy="addItemButton"
         src="@/images/plus_white.png"
         @click="sendMessage"
    />
  </div>
  <p>{{ errorMessage }}</p>
</template>

<style lang="scss" scoped>
@import "src/assets/fontsAndColors";

#new-item {
  background-color: rgba($primary-color, 0.4);

  input {
    width: auto;
    background-color: rgba($primary-color, 0);
    border: none;

    &:focus {
      outline: none;

      &::placeholder {
        opacity: 0;
      }
    }
  }

}

img {
  height: 60px;
  width: auto;
  opacity: 0.6;

  &:hover {
    opacity: 1 !important;
    cursor: pointer;
  }
}

p {
  height: 2em;
  font: $font-error;
  color: $error-color;
  opacity: 0.6;
}

</style>