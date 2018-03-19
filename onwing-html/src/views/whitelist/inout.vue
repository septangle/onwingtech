<template>
<div class="animated fadeIn">
    <div>
        <!--显示大图弹框-->
        <Modal
            v-model="showPictureModal"
            :closable="false"
            :mask-closable="false">
            <div id="container">
                <img :src="bigPhotoUrl" style="width:100%;">
            </div>
            <div slot="footer">
                <Button size="large" @click="closeModal">关闭</Button>
            </div>
        </Modal>
    </div>
    <Row>
        <Col :md="24">
            <div>
                <Input v-model="searchContent" placeholder="请输入需要查询的内容" style="width: 300px;">
                    <Button slot="append" icon="ios-search" @click="getInoutDate(1,pagesize)"></Button>
                </Input>
                <div style="position:relative;margin-top: 10px;">
                    <!-- 表格开始 -->
                    <Table stripe border height="auto" :columns="columns_title" :data="inout_data" ref="table"></Table>
                    <!-- 表格结束 -->

                    <!-- 表格内容加载提示蒙板开始 -->
                    <div style="position:absolute;top:0px;width:100%;height:100%;display: flex;align-items: center;justify-content: center;background: rgba(210, 216, 222, 0.5);" v-if="page_loading">
                        <Spin size="large"></Spin>
                        <h6 style="color:#2d8cf0;margin-top:10px;">正在获取数据...</h6>
                    </div>
                    <!-- 表格内容加载提示蒙板结束 -->
                </div>
                <div style="float:left;margin-top: 20px;">
                <Button type="primary" size="large" @click="exportData(1)"><Icon type="ios-download-outline"></Icon>&nbsp;导出数据</Button>
                </div>
                <!-- 分页开始 -->
                <div style="float:right;margin-top: -30px;">
                    <Page :total="datacount" :page-size="pagesize" show-total @on-change="changePage" style="text-align:right;margin-top:50px"></Page>
                </div>
                <!-- 分页结束 -->
            </div>
        </Col>
    </Row>
    <!--
    <div class="dialog-showBigPhoto">
        <span class="close" @click="hideDialog">X</span>
        <p>
            <img :src="bigPhotoUrl" />
        </p>
    </div>
    <div class="mask" v-show="showDialog"></div>
    -->
</div>
</template>
<script>
import GlobalServer from '../../config.js';
import axios from 'axios';
import Cookies from 'js-cookie';
export default {
    name: 'whitelist_inout_index',
    data () {
        return {
            showPictureModal: false,
            pageID:'inout',
            bigPhotoUrl:'',
            progresshow:false,
            progresscount:0,
            progresstatus:'active',
            progressspeed:0,
            inout_data:[],
            inout_thumb: [],
            inout_page_data:[],
            datacount: 0,
            pageindex:1,
            pagesize: 20,
            communityID:'',
            searchContent:'',
            page_loading:false,
            data_loading:false,
            columns_title:[
                {
                    title: 'ID',
                    key: 'id',
                    ellipsis:'true',
                    width: 80,
                    align: 'center',
                    sortType: 'desc'
                },{
                    title: '小区名称',
                    key: 'communityName',
                    ellipsis:'true',
                    width: 110,
                    align: 'center'
                },{
                    title: '缩略图',
                    key: 'photoUrl',
                    width: 120,
                    align: 'center',
                    render: (h,params) => {
                        return h('img',{
                            attrs: {
                                src: this.inout_thumb[params.row._index],
                                style: 'width:100%;height:auto;margin-top:4px;'
                            },
                            on: {
                                click: () => {
                                    let rowIndex = params.row._index;
                                    this.bigPhotoUrl = this.inout_thumb[rowIndex];
                                    this.showPictureModal = true;
                                }
                            }
                        })
                    }
                },{
                    title: '姓名',
                    key: 'householdName',
                    ellipsis:'true',
                    width: 110,
                    align: 'center',
                    render: (h,params) => {
                        return h('p',{
                            domProps: {
                                innerHTML:this.inout_data[params.row._index].householdName
                            },
                            attrs:{
                               style:'cursor:default'
                            },
                            on: {
                                click: () => {
                                    let rowIndex = params.row._index;
                                    let argu = {
                                            householdID: this.inout_data[rowIndex].householdID,
                                            pageID: this.pageID
                                        };
                                    console.info(this.inout_data);
                                    this.$router.push({
                                        name: 'whitelist_info_index',
                                        params: argu
                                    });
                                }
                            }
                        })
                    }
                },{
                    title: '住址',
                    key: 'roomPath',
                    width: 170,
                    align: 'center'
                },{
                    title: '进入时间',
                    key: 'outOffTime',
                    align: 'center'
                },{
                    title: '门禁设备号',
                    key: 'controlID',
                    align: 'center'
                },{
                    title: '摄像头设备号',
                    key: 'cameraID',
                    align: 'center'
                }
            ]
        }
    },
    methods: {
        init() {
            let access = Cookies.get('access');
            if (access === '0') {
                this.communityID = -1;
            } else {
                this.communityID = sessionStorage.getItem('communityID');
            }
        },
        getInoutDate(currentPage,pageSize) {
            /* axios有自己的作用域,无法获取vue实例,所以要将vue实例的this传到一个变量中以便在axios中调用 */
            var _this = this;
            let communityID = sessionStorage.getItem('searchCommunityID'),
                searchContent = _this.searchContent;
            /* 将page_loading值设置为true,用以在获取数据时显示‘正在加载数据’的蒙板 */
            _this.page_loading = true;
            /* 获取所有住户信息并将值传入进inout_data数组 */
            axios.get(GlobalServer.findAllAccessRecord + '?page=' + currentPage + '&pageSize=' + pageSize + '&communityId=' + communityID + '&searchContent=' + searchContent)
            .then(function(response){
                let data = response.data;
                if(data.error === null){
                    _this.datacount = data.totalNumber;
                    /* 定义tempArr数组，临时存放返回的出入记录 */
                    // let tempArr = [];
                    // tempArr = data.houseAccessRecordDtosList;

                    /* 遍历tempArr数组，将数组中每一条出入记录对象中的单元号和房号进行拼接 */
                    _this.inout_data = data.houseAccessRecordDtosList.map(function(value){
                        let tempObj = {};
                        let tempDate = _this.setDate(value.outOffTime);
                        tempObj.id = value.id;
                        tempObj.householdID = value.householdId;
                        tempObj.communityName = value.communityName;
                        tempObj.householdName = value.householdName;
                        tempObj.roomPath = value.roomPath.replace(/\//g,'-').replace(/^\-/,'');
                        tempObj.outOffTime = tempDate;
                        //controlID门禁设备号,cameraID摄像头设备号
                        tempObj.controlID = value.controlId;
                        tempObj.cameraID = value.cameraId;
                        _this.inout_thumb.push(GlobalServer.ServerHost + 'onwing-web/' + value.photoUrl);
                        return tempObj;
                    });
                    //console.info(_this.inout_data);
                    /* 如果数据条目数小于每页显示的条目数,则将所有数据传入分页数据对象,
                       如果数据条目数大于每页显示的条目数,则将第一页要显示的数据传入分页数据对象 */
                    // if(_this.datacount <= _this.pagesize) {
                    //     _this.inout_page_data = _this.inout_data;
                    // } else {
                    //     _this.inout_page_data = _this.inout_data.slice(0,_this.pagesize-1);
                    // }

                    /* 将page_loading值设置为false,隐藏'下在加载数据'的蒙板 */
                    _this.page_loading = false;
                }
            })
            .catch(function(error){
                console.info('error=' + error);
            })
        },
        changePage(index){
            //入参index <Page>组件中on-change事件的返回值，表示页码改变后的页码
            // start 每页的开始数据
            // let start = (index - 1) * this.pagesize;
            // end 每页的结束数据
            // let end = index * this.pagesize;

            //this.inout_page_data = this.inout_data.slice(start,end);
            this.getInoutDate(index,this.pagesize);
        },
        exportData (type) {
            if (type === 1) {
                this.$refs.table.exportCsv({
                    filename: '原始数据',
                    columns: this.columns_title,
                    data: this.inout_data
                });
            } else if (type === 2) {
                this.$refs.table.exportCsv({
                    filename: '排序和过滤后的数据',
                    original: false
                });
            }
        },
        setDate (value) {
            let temp = new Date(value);
            let year = temp.getFullYear() + '年',
                month = temp.getMonth() + 1 + '月',
                date = temp.getDate() + '日',
                hours = temp.getHours() + '点',
                minutes = temp.getMinutes() + '分',
                second = temp.getSeconds() + '秒',
                fullDate = '';
            return fullDate = year + month + date + hours + minutes + second;
        },
        closeModal () {
            this.showPictureModal = false;
        }
    },
    beforeCreate() {},
    created() {
        this.init();
        this.getInoutDate(1,this.pagesize);
    },
    beforeMount() {},
    mounted() {

    },
    activated() {},
    watch:{
        searchContent(arg1){
            if (arg1.length === 0) {
              this.getInoutDate(1,this.pagesize);
            }
        }
    }
}
</script>
<style type="text/css" scoped>
  .ivu-tag-dot {
    border: none!important;
  }

  tr.ivu-table-row-hover td .ivu-tag-dot {
    background-color: #ebf7ff!important;
  }

  .demo-i-circle-custom h1 {
    color: #3f414d;
    font-size: 10px;
    font-weight: normal;
  }

  .demo-i-circle-custom p {
    color: #657180;
    font-size: 8px;
    margin: 5px 0 2px;
  }

  .demo-i-circle-custom span {
    display: block;
    padding-top: 15px;
    color: #657180;
    font-size: 10px;
  }

  .demo-i-circle-custom span :before {
    content: '';
    display: block;
    width: 50px;
    height: 1px;
    margin: 0 auto;
    background: #e0e3e6;
    position: relative;
    top: -20px;
  }

  .demo-i-circle-custom span i {
    font-style: normal;
    color: #3f414d;
  }

  .ivu-btn.ivu-btn-primary.ivu-btn-small:not(.ivu-btn-loading) {
    padding: 2px 10px!important;
  }

  td.ivu-table-expanded-cell {
    background-color: white!important;
  }
</style>
