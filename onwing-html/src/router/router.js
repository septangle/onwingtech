import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: resolve => { require(['@/views/login.vue'], resolve); }
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: resolve => { require(['@/views/error-page/404.vue'], resolve); }
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: resolve => { require(['@/views/error-page/403.vue'], resolve); }
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: resolve => { require(['@/views/error-page/500.vue'], resolve); }
};

export const preview = {
    path: '/preview',
    name: 'preview',
    component: resolve => { require(['@/views/form/article-publish/preview.vue'], resolve); }
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: resolve => { require(['@/views/main-components/lockscreen/components/locking-page.vue'], resolve); }
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        {
            path: 'home',
            title: '首页',
            name: 'home_index',
            component: resolve => { require(['@/views/whitelist/list.vue'], resolve); }
        },
        {
            path: 'whitelist_info/:cardNumber',
            title: '员工详情',
            name: 'whitelist_info_index',
            component: resolve => { require(['@/views/whitelist/info.vue'], resolve); }
        }
        /*
        {
            path: 'guestlist_info/:identifyCard',
            title: '访客详情',
            name: 'guestlist_info_index',
            component: resolve => { require(['@/views/guestlist/guest_info.vue'], resolve); }
        }
        */
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/whitelist',
        icon: 'key',
        name: 'whitelist_list',
        title: '住户列表',
        component: Main,
        children: [
            { path: 'whitelist_list', title: '员工列表', name: 'whitelist_list_index', component: resolve => { require(['@/views/whitelist/list.vue'], resolve); } }
        ]
    },
    {
        path: '/whitelist',
        icon: 'key',
        name: 'whitelist_inout',
        title: '出入记录',
        component: Main,
        children: [
            { path: 'whitelist_inout', title: '出入记录', name: 'whitelist_inout_index', component: resolve => { require(['@/views/whitelist/inout.vue'], resolve); } }
        ]
    },
    /*
    {
        path: '/guestlist',
        icon: 'key',
        name: 'guestlist_list',
        title: '访客列表',
        component: Main,
        children: [
            {path:'guestlist_list', title: '访客列表', name: 'guestlist_list_index', component: resolve => {require(['@/views/guestlist/guest_list.vue'],resolve); } }
        ]
    },
    {
        path: '/guestlist',
        icon: 'key',
        name: 'guestlist_inout',
        title: '访客记录',
        component: Main,
        children: [
            { path: 'guestlist_inout', title: '访客记录', name: 'guestlist_inout_index', component: resolve => { require(['@/views/guestlist/guest_inout.vue'], resolve); } }
        ]
    },
    {
        path: '/guestlist',
        icon: 'key',
        name: 'guestlist_add',
        title: '访客登记',
        component: Main,
        children: [
            { path: 'guestlist_add', title: '访客登记', name: 'guestlist_add_index', component: resolve => { require(['@/views/guestlist/guest_add.vue'], resolve); } }
        ]
    },
    */
    {
        path: '/whitelist/',
        icon: 'key',
        name: 'whitelist_add',
        title: '添加住户',
        component: Main,
        children: [
            { path: 'whitelist_add', title: '添加员工', name: 'whitelist_add_index', component: resolve => { require(['@/views/whitelist/add.vue'], resolve); } }
        ]
    }
/*    {
        path: '/photoupload',
        icon: 'key',
        name: 'photoupload',
        title: '照片上传',
        component: Main,
        children: [
            { path: 'index', title: '照片上传', name: 'photoupload_index', component: resolve => { require(['@/views/photoup/photoup.vue'], resolve); } }
        ]
    }*/
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    ...appRouter,
    page500,
    page403,
    page404
];
