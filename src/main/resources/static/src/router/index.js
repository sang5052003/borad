import Vue from 'vue'

import Router from 'vue-router'
import Main from '@/components/Main'
import DetailPost from '@/components/DetailPost'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'main',
      component: Main,
      children: [
        {
          path: 'post/title',
          name: 'detailpost',
          component: DetailPost
        }
      ]
    }
  ]
})
