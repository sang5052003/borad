<template>
  <div class="main">
    <h3>{{ msg }}</h3>
    <ChildComponent></ChildComponent>
    <md-spinner md-indeterminate></md-spinner>
    <md-spinner md-indeterminate class="md-accent"></md-spinner>
    <md-spinner md-indeterminate class="md-warn"></md-spinner>
  </div>
</template>
<script>
  import ChildComponent from './ChildComponent.vue'

  export default {
    name: 'Main',
    components: {
      ChildComponent
    },
    data () {
      return {
        msg: 'parentMSG',
        people: null,
      }
    },
    created: function () {
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
