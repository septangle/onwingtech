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
            component: resolve => { require(['@/views/household/list.vue'], resolve); }
        },
        {
            path: 'household_info/:cardNumber',
            title: '住户详情',
            name: 'household_info',
            component: resolve => { require(['@/views/household/info.vue'], resolve); }
        }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/household',
        icon: 'key',
        name: 'household',
        title: '住户列表',
        component: Main,
        children: [
            { path: 'index', title: '住户列表', name: 'household_index', component: resolve => { require(['@/views/household/list.vue'], resolve); } }
        ]
    },
    {
        path: '/household/',
        icon: 'key',
        name: 'household_add',
        title: '添加住户',
        component: Main,
        children: [
            { path: 'add', title: '添加住户', name: 'household_add_index', component: resolve => { require(['@/views/household/add.vue'], resolve); } }
        ]
    },
    {
        path: '/inout',
        icon: 'key',
        name: 'inoutlist',
        title: '出入记录',
        component: Main,
        children: [
            { path: 'index', title: '出入记录', name: 'inoutlist_index', component: resolve => { require(['@/views/inout/list.vue'], resolve); } }
        ]
    },
    {
        path: '/photoupload',
        icon: 'key',
        name: 'photoupload',
        title: '照片上传',
        component: Main,
        children: [
            { path: 'index', title: '照片上传', name: 'photoupload_index', component: resolve => { require(['@/views/photoup/photoup.vue'], resolve); } }
        ]
    }
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
