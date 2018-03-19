import Cookies from 'js-cookie';

const user = {
    state: {},
    mutations: {
        logout (state, vm) {
            Cookies.remove('user');
            Cookies.remove('access');
            sessionStorage.clear();
            localStorage.clear();
            //Cookies.remove('userType');
            //Cookies.remove('communityID');
            //Cookies.remove('communityName');
            /* Cookies.remove('password'); */
            // 恢复默认样式
            /* let themeLink = document.querySelector('link[name="theme"]');
            themeLink.setAttribute('href', '');
            // 清空打开的页面等数据，但是保存主题数据
            let theme = '';
            if (localStorage.theme) {
                theme = localStorage.theme;
            }
            if (theme) {
                localStorage.theme = theme;
            } */
        }
    }
};

export default user;
