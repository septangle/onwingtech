<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="main" :class="{'main-hide-text': shrink}">
        <div class="sidebar-menu-con" :style="{width: shrink?'60px':'200px', overflow: shrink ? 'visible' : 'auto'}">
            <shrinkable-menu
                :shrink="shrink"
                @on-change="handleSubmenuChange"
                :theme="menuTheme"
                :before-push="beforePush"
                :open-names="openedSubmenuArr"
                :menu-list="menuList">
                <div slot="top" class="logo-con">
                    <img v-show="!shrink" src="../images/logo-ow.png" key="max-logo" />
                    <img v-show="shrink" src="../images/logo-ow.png" key="min-logo" />
                </div>
            </shrinkable-menu>
        </div>
        <div class="main-header-con" :style="{paddingLeft: shrink?'60px':'200px'}">
            <div class="main-header">
                <div class="navicon-con">
                    <Button :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
                        <Icon type="navicon" size="32"></Icon>
                    </Button>
                </div>
                <div class="header-middle-con">
                    <div class="main-breadcrumb">
                        <breadcrumb-nav :currentPath="currentPath"></breadcrumb-nav>
                    </div>
                </div>
                <div class="header-avator-con">
                    <!-- <full-screen v-model="isFullScreen" @on-change="fullscreenChange"></full-screen> -->
                    <!-- <lock-screen></lock-screen> -->
                    <!-- <message-tip v-model="mesCount"></message-tip> -->
                    <!-- <theme-switch></theme-switch> -->
                    <div class="user-dropdown-menu-con">
                        <Row type="flex" justify="end" align="middle" class="user-dropdown-innercon">
                            <div v-if="this.access === '0'">
                                <Dropdown transfer="true" trigger="click">
                                    <a href="javascript:void(0)">
                                        <span class="main-user-name">{{communityName}}</span>
                                        <Icon type="arrow-down-b"></Icon>
                                    </a>
                                  <DropdownMenu slot="list">
                                      <DropdownItem name="" v-for="item in communityNameList" :value="item" :key="item"  @click.native="handleClickCommunityDropdown">{{item}}</DropdownItem>
                                  </DropdownMenu>
                                </Dropdown>
                            </div>
                            <div v-if="this.access === '1'">
                                <span>{{communityName}}</span>
                                <a href="javascript:void(0)" @click="doInput()">(上传拓扑)</a>
                                <div style="display: none">
                                    <input type="file" accept="" ref="upCsv" @change="fileChange()">
                                </div>
                            </div>
                            <div v-if="this.access === '2'">
                                <span>{{communityName}}</span>
                            </div>
                            <Dropdown transfer="true" trigger="click" @on-click="handleClickUserDropdown">
                                <a href="javascript:void(0)">
                                    <span class="main-user-name">{{ userName }}</span>
                                    <Icon type="arrow-down-b"></Icon>
                                </a>
                                <DropdownMenu slot="list">
                                    <!-- <DropdownItem name="ownSpace">个人中心</DropdownItem> -->
                                    <DropdownItem name="loginout" >退出登录</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                            <Avatar :src="avatorPath" style="background: #619fe7;margin-left: 10px;"></Avatar>
                        </Row>
                    </div>
                </div>
            </div>
            <div class="tags-con">
                <tags-page-opened :pageTagsList="pageTagsList"></tags-page-opened>
            </div>
        </div>
        <div class="single-page-con" :style="{left: shrink?'60px':'200px'}">
            <div class="single-page">
                <keep-alive :include="cachePage">
                    <router-view></router-view>
                </keep-alive>
            </div>
        </div>
    </div>
</template>
<script>
    import shrinkableMenu from './main-components/shrinkable-menu/shrinkable-menu.vue';
    import tagsPageOpened from './main-components/tags-page-opened.vue';
    import breadcrumbNav from './main-components/breadcrumb-nav.vue';
    /* import fullScreen from './main-components/fullscreen.vue';
    import lockScreen from './main-components/lockscreen/lockscreen.vue';
    import messageTip from './main-components/message-tip.vue';
    import themeSwitch from './main-components/theme-switch/theme-switch.vue'; */
    import Cookies from 'js-cookie';
    import util from '@/libs/util.js';
    import GlobalServer from '../config.js';
    import axios from 'axios';

    export default {
        components: {
            shrinkableMenu,
            tagsPageOpened,
            breadcrumbNav,
            /* fullScreen,
            lockScreen,
            messageTip,
            themeSwitch */
        },
        data () {
            return {
                shrink: false,
                userName: '',
                access: '',
                communityName: '',
                communityID:'',
                communityNameList:[],
                communityIDList:[],
                isFullScreen: false,
                openedSubmenuArr: this.$store.state.app.openedSubmenuArr,
                csvFile:''
            };
        },
        computed: {
            menuList () {
                return this.$store.state.app.menuList;
            },
            pageTagsList () {
                return this.$store.state.app.pageOpenedList; // 打开的页面的页面对象
            },
            currentPath () {
                return this.$store.state.app.currentPath; // 当前面包屑数组
            },
            avatorPath () {
                return localStorage.avatorImgPath;
            },
            cachePage () {
                return this.$store.state.app.cachePage;
            },
            lang () {
                return this.$store.state.app.lang;
            },
            menuTheme () {
                return this.$store.state.app.menuTheme;
            },
            mesCount () {
                return this.$store.state.app.messageCount;
            }
        },
        methods: {
            init () {
                let pathArr = util.setCurrentPath(this, this.$route.name);
                this.$store.commit('updateMenulist');

                if (pathArr.length >= 2) {
                    this.$store.commit('addOpenSubmenu', pathArr[1].name);
                }

                //读取COOKIE中的用户名，用户类型，小区名称，小区ID
                this.userName = Cookies.get('user');
                this.access = Cookies.get('access');
                this.communityNameList = sessionStorage.getItem('communityName').split(',');
                this.communityIDList = sessionStorage.getItem('communityID').split(',');

                if(this.access === '0') {
                  this.communityNameList.unshift('所有小区');
                  this.communityIDList.unshift(-1);
                  this.communityName = '所有小区';
                } else {
                  this.communityName = this.communityNameList[0];
                  this.communityID = this.communityIDList[0];
                }

                //let messageCount = 3;
                //this.messageCount = messageCount.toString();
                this.checkTag(this.$route.name);
                //this.$store.commit('setMessageCount', 3);
                //this.communityList =
            },

            toggleClick () {
                this.shrink = !this.shrink;
            },
            handleClickCommunityDropdown (event) {
                let str = event.target.innerText,
                    index = this.communityNameList.indexOf(str);
                this.communityName = str;
                this.communityID = this.communityIDList[index];
                sessionStorage.setItem('searchCommunityID',this.communityID);
            },
            handleClickUserDropdown (name) {
                if (name === 'ownSpace') {
                    util.openNewPage(this, 'ownspace_index');
                    this.$router.push({
                        name: 'ownspace_index'
                    });
                } else if (name === 'loginout') {
                    // 退出登录
                    this.$store.commit('logout', this);
                    this.$store.commit('clearOpenedSubmenu');
                    this.$router.push({
                        name: 'login'
                    });
                }
            },
            checkTag (name) {
                let openpageHasTag = this.pageTagsList.some(item => {
                    if (item.name === name) {
                        return true;
                    }
                });
                if (!openpageHasTag) { //  解决关闭当前标签后再点击回退按钮会退到当前页时没有标签的问题
                    util.openNewPage(this, name, this.$route.params || {}, this.$route.query || {});
                }
            },
            handleSubmenuChange (val) {
                // console.log(val)
            },
            beforePush (name) {
                // if (name === 'accesstest_index') {
                //     return false;
                // } else {
                //     return true;
                // }
                return true;
            },
            fullscreenChange (isFullScreen) {
                // console.log(isFullScreen);
            },
            doInput () {
              //console.info('fileup');
              this.$refs.upCsv.accept = '.csv';
              this.$refs.upCsv.click();
            },
            fileChange () {
                this.file = this.$refs.upCsv.files[0];
                console.info(this.file);
                this.upFile();
            },
            upFile () {
                let _this = this,
                    formdata = new FormData(),
                    config = {
                        headers:{
                            'Content-Type':'application/x-www-form-urlencoded'
                        }
                    };
                formdata.append('file',_this.file);
                formdata.append('communityId',_this.communityID);
                axios.post(GlobalServer.importRoomsFromCvs,formdata,config)
                    .then(function(response){
                        let data = response.data;
                        if(data.error === null) {
                            _this.$Message.success('上传成功！');
                            _this.file = '';
                        }
                    })
                    .catch(function(error){
                        console.info(error);
                    });
            }
        },
        watch: {
            '$route' (to) {
                this.$store.commit('setCurrentPageName', to.name);
                let pathArr = util.setCurrentPath(this, to.name);
                if (pathArr.length > 2) {
                    this.$store.commit('addOpenSubmenu', pathArr[1].name);
                }
                this.checkTag(to.name);
                localStorage.currentPageName = to.name;
            },
            lang () {
                util.setCurrentPath(this, this.$route.name); // 在切换语言时用于刷新面包屑
            }
        },
        mounted () {
            this.init();
        },
        created () {
            // 显示打开的页面的列表
            this.$store.commit('setOpenedList');
        }
    };
</script>
