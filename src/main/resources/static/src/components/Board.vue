<template>
  <md-table-card>
    <md-toolbar>
      <h1 class="md-title">{{msg}}</h1>
      <!--<md-button class="md-icon-button">-->
      <!--<md-icon>filter_list</md-icon>-->
      <!--</md-button>-->

      <md-button class="md-icon-button" @click="deleteClick">
        <md-icon>delete</md-icon>
      </md-button>

      <md-button class="md-icon-button">
        <md-icon>search</md-icon>
      </md-button>

      <write-form @refreshBoard="initList"></write-form>

    </md-toolbar>

    <md-table md-sort="id" md-sort-type="desc" @select="onSelect" @sort="onSort">
      <md-table-header>
        <md-table-row>
          <md-table-head md-sort-by="title" :md-numeric="false">Title</md-table-head>
          <md-table-head md-sort-by="contents" :md-numeric="false"
                         md-tooltip="The total amount of food energy and the given serving size">Contents
          </md-table-head>
          <md-table-head md-sort-by="id" :mdNumeric="false">ID</md-table-head>
        </md-table-row>
      </md-table-header>

      <md-table-body>
        <md-table-row v-for="(row, rowIndex) in pagePost" :key="rowIndex" :md-item="row" :md-auto-select="true"
                      :md-selection="true">
          <md-table-cell v-for="(column, columnIndex) in row" :key="columnIndex" :md-numeric="false"
                         v-if="columnIndex !== '_links' && columnIndex !== 'reply'" @click.native="itemSelect(row._links)">
            {{ column }}
          </md-table-cell>

        </md-table-row>
      </md-table-body>
    </md-table>

    <md-table-pagination
      :md-size="page.size"
      :md-total="page.total"
      :md-page="page.page"
      :md-label="page.label"
      :md-separator="page.separator"
      :md-page-options="[5, 10, 25, 50]"
      @pagination="onPagination"
      ></md-table-pagination>
  </md-table-card>
</template>

<script>
  import ChildComponent from './ChildComponent.vue'
  import MdTableCell from "../../node_modules/vue-material/src/components/mdTable/mdTableCell.vue";
  import MdButton from "../../node_modules/vue-material/src/components/mdButton/mdButton.vue";
  import WriteForm from "./WriteForm.vue"

  export default {
    name: 'Main',
    components: {
      MdButton,
      MdTableCell,
      ChildComponent,
      WriteForm
    },
    data: () => ({
      msg: "Board",
      board: [],
      posts: [],
      checked: null,
      checkList: [],
      postIds: [],
      pagePost: [],
      page: {
        size: "5",
        total: "10",
        page: "1",
        label: "Rows",
        separator: "of" //?
      }
    }),
    created: function () {
      var self = this
      self.initList()
    },
    methods: {
      onSelect: function (row) {
        var self = this
        self.checkList = row
      },
      onSort: function () {

      },
      onPagination: function (page) {
        var self = this
        self.page.size = page.size
        self.page.page = page.page

        if (page.size > self.posts.length){
          page.size = self.posts.length
        }
        self.pagePost.splice(0, self.pagePost.length)
        var startPage = (page.page - 1) * page.size
        for (var i = startPage; i < (startPage + page.size); i++){
          self.pagePost.push(self.posts[i])
        }
      },
      itemSelect: function (e) {
        var self = this
        var path = e.post.href
        path = path.charAt(path.length - 1)
        self.$router.push({
          path: 'post/id/' + path
//          path: self.$route.query.redirect
        })
      },
      deletePost: function (id) {
        var self = this
//        self.checkList
        var xhr = new XMLHttpRequest()
        xhr.open('DELETE', 'http://localhost:8080/post/' + id)
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          self.initList()
        }
        xhr.send()
      },
      deleteClick: function () { //여러번 통신
        //id valid
        var self = this
//        self.checkList

        for (var i = 0; i < self.checkList.length; i++){
          var path = self.checkList[i]._links.post.href
          path = path.charAt(path.length - 1)
          self.deletePost(path)
        }
      },
      initList: function () {
        var xhr = new XMLHttpRequest()

        var self = this

        xhr.open('GET', 'http://localhost:8080/post')
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          self.posts = JSON.parse(xhr.responseText)._embedded.posts

          //최초 페이지
          self.page.total =  self.posts.length
          for (var i = 0; i < self.page.page * self.page.size; i++){
            self.pagePost.push(self.posts[i])
          }
//          self.page.total= self.posts.length
//          self.onPagination(self.page)

        }
        xhr.send()
      },


    }
  };

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

  .btn {
    font-weight: normal;
    font-size: x-small;
    text-align: center;
  }

</style>
