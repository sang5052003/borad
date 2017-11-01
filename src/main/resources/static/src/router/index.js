import Vue from 'vue'

import Router from 'vue-router'
import Home from '@/components/Home'
import Board from '@/components/Board'
import DetailPost from '@/components/DetailPost'
import WriteForm from '@/components/WriteForm'

Vue.use(Router)

Vue.component('home', Home)
Vue.component('board', Board)
Vue.component('detail-post', DetailPost)
Vue.component('write-form', WriteForm)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/post',
      name: 'home',
      component: Home,
      children: [
        {
          path: 'post/id/:id',
          name: 'detailpost',
          component: DetailPost
        },
        {
          path: 'post',
          name: 'posts',
          components: {
            default: Board,
            form: WriteForm
          }
        }
      ]
    }
  ]
})
