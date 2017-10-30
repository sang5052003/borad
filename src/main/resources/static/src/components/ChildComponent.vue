<template>
  <div class="child">
    <h3>{{ msg }}</h3>
    <ul>
      <li v-for="item in people" :key="item.firstName">{{item.firstName}} {{item.lastName}}</li>
    </ul>
  </div>
</template>
<script>
  export default {
    name: 'ChildComponent',
    data () {
      return {
        msg: 'childMSG',
        people: null
      }
    },
    created: function () {
      var xhr = new XMLHttpRequest()

      var self = this
      xhr.open('GET', 'http://localhost:8080/people')
      xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')

      xhr.onload = function () {
        self.people = JSON.parse(xhr.responseText)._embedded.people

        Array.prototype.forEach.call(self.people, child => {
          console.log(child.firstName)
        })
      }
      xhr.send()
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
