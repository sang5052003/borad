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
        total: "30",
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

        console.log('page')
        console.log(page)

        //전체 데이터 보다 클경우 (page.size)
        if (page.size >= self.posts.length){
          //1page
          page.page = 1
        }

        //cur page 갱신
        //시작인덱스가 전체데이터에서 몇 번째 페이지에 있는가(size에 따라서)
        var pageCount = self.posts.length / page.size //전체 페이지 갯수
        var prevPageSize = self.page.size
        var startIdx = (page.page - 1) * prevPageSize //시작인덱스
        var curPage = startIdx / page.size + 1

        //변경된 page, size 저장
        self.page.page = curPage
        self.page.size = page.size

        if (page.size > self.posts.length){
          page.size = self.posts.length
        }
        self.pagePost.splice(0, self.pagePost.length) //pagePost 비우기

        self.displayList(startIdx, startIdx + page.size)
//        for (var i = startIdx; i < (startIdx + page.size); i++){
//          if (self.posts[i] === undefined) break
//          self.pagePost.push(self.posts[i])
//        }
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
      deletePost: function (path) {
        var self = this
//        self.checkList
        var xhr = new XMLHttpRequest()
        xhr.open('DELETE', path)
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          self.initList()
        }
        xhr.send()
      },
      deleteClick: function () { //여러번 통신해서 지우기(checked 데이터)
        //id valid
        var self = this
//        self.checkList

        //자를때 잘못자름(무조건 한자리로 생각)
        for (var i = 0; i < self.checkList.length; i++){
          var path = self.checkList[i]._links.post.href
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

          //뒤집기..
          self.posts.reverse()

          //최초 페이지
          self.page.total =  self.posts.length //20개?
          console.log('total '+self.page.total)
          self.page.page = 1
          self.pagePost.splice(0, self.pagePost.length) //pagePost 비우기 (시작인덱스, 갯수)

          //뒤에서 부터 나오도록
          self.displayList(0, self.page.page * self.page.size)


//          for (var i = 0; i < self.page.page * self.page.size; i++){
//            self.pagePost.push(self.posts[i])
//          }
//          self.page.total= self.posts.length
//          self.onPagination(self.page)

        }
        xhr.send()
      },
      displayList: function (startIdx, endIdx) {
        var self = this

        console.log(startIdx)
        console.log(endIdx)

        for (var i = startIdx; i < endIdx; i++){
          if (self.posts[i] === undefined) break
          self.pagePost.push(self.posts[i])
        }
      }


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
