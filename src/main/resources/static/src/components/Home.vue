<template>
  <div class="home">
    <h2>{{ msg }}</h2>
    <!--<button @click="goList">list</button>-->
    <!--<router-link to="/post">/post</router-link>-->

    <ul>
    </ul>
    <div class="fluid">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>

  export default {
    name: 'home',
    data () {
      return {
        msg: 'Board Exam'
      }
    },
    created: function () {
    },
    methods: {
      goList: function () {
        this.$router.push({
          path: 'post/title/title1'
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
    text-align: center;
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

  .fluid {
    position: relative;
    height: calc(100vh - 64px);
    overflow-y: scroll;
    overflow-x: hidden;
    width: 100%;
  }
</style>
