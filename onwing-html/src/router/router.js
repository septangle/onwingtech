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
            component: resolve => { require(['@/views/whitelist/inout.vue'], resolve); }
        },{
            path: 'whitelist_info',
            title: '查看住户信息',
            name: 'whitelist_info_index',
            component: resolve => { require(['@/views/whitelist/info.vue'], resolve); }
        },
        {
            path: 'whitelist_edit',
            title: '修改住户信息',
            name: 'whitelist_edit_index',
            component: resolve => { require(['@/views/whitelist/edit.vue'], resolve); }
        }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/whitelist',
        icon: 'key',
        name: 'whitelist_inout',
        title: '出入记录',
        component: Main,
        access: [0,1,2],
        children: [
            { path: 'whitelist_inout', title: '出入记录', name: 'whitelist_inout_index', component: resolve => { require(['@/views/whitelist/inout.vue'], resolve); } }
        ]
    },
    {
        path: '/whitelist',
        icon: 'key',
        name: 'whitelist_list',
        title: '住户列表',
        component: Main,
        access:[0,1],
        children: [
            { path: 'whitelist_list', title: '住户列表', name: 'whitelist_list_index', component: resolve => { require(['@/views/whitelist/list.vue'], resolve); } }
        ]
    },
    {
        path: '/whitelist',
        icon: 'key',
        name: 'whitelist_list_access2',
        title: '住户列表',
        component: Main,
        access: 2,
        children: [
            { path: 'whitelist_list_access2', title: '住户列表', name: 'whitelist_list_access2_index', component: resolve => { require(['@/views/whitelist/list_access2.vue'], resolve); } }
        ]
    },
    {
        path: '/whitelist',
        icon: 'key',
        name: 'whitelist_add',
        title: '添加住户',
        component: Main,
        access: [0,1],
        children: [
            { path: 'whitelist_add', title: '添加住户', name: 'whitelist_add_index', component: resolve => { require(['@/views/whitelist/add.vue'], resolve); } }
        ]
    },
    {
        path: '/community',
        icon: 'key',
        name: 'communityManage',
        title: '小区管理',
        component: Main,
        access: 0,
        children: [
            {
                path: 'community_list',
                icon: 'compose',
                title: '小区列表',
                name: 'community_list_index',
                component: resolve => { require(['@/views/community/list.vue'], resolve); }
            },
            {
                path: 'community_add',
                icon: 'compose',
                title: '添加小区',
                name: 'community_add_index',
                component: resolve => { require(['@/views/community/add.vue'], resolve); }
            }
        ]
    },
    {
        path: '/user',
        icon: 'key',
        name: 'userManage',
        title: '账号管理',
        component: Main,
        access: [0,1],
        children: [
            {
                path: 'user_add',
                icon: 'compose',
                title: '添加账号',
                name: 'user_add_index',
                component: resolve => { require(['@/views/user/add.vue'], resolve); }
            },
            {
                path: 'user_list',
                icon: 'compose',
                title: '账号列表',
                name: 'user_list_index',
                component: resolve => { require(['@/views/user/list.vue'], resolve); }
            }
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
