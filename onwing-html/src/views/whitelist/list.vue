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
                    <Button slot="append" icon="ios-search" @click="getHouseholdDate(1,pagesize)"></Button>
                </Input>
                <div style="position:relative;margin-top: 10px;">
                    <!-- 表格开始 -->
                    <Table stripe border height="auto" :columns="columns_title" :data="household_data" ref="table"></Table>
                    <!-- 表格结束 -->
                    <!-- 表格内容加载提示蒙板开始 -->
                    <div style="position:absolute;top:0px;width:100%;height:100%;display: flex;align-items: center;justify-content: center;background: rgba(210, 216, 222, 0.5);" v-if="page_loading">
                        <Spin size="large"></Spin>
                        <h6 style="color:#2d8cf0;margin-top:10px;">正在获取数据...</h6>
                    </div>
                    <!-- 表格内容加载提示蒙板结束 -->
                </div>

                <div style="float:left;margin-top:20px">
                  <Button type="primary" size="large" @click="exportData(1)"><Icon type="ios-download-outline"></Icon>&nbsp;导出数据</Button>

                <!-- <Button type="primary" size="large" @click="exportData(2)"><Icon type="ios-download-outline"></Icon>导出排序和过滤后的数据</Button> -->
                </div>
                <!-- 分页开始 -->
                <div style="float:right;margin-top:-30px">
                  <Page ref="page" :total="datacount" :page-size="pagesize" show-total @on-change="changePage" style="text-align:right;margin-top:50px"></Page>
                </div>
                <!-- 分页结束 -->
            </div>
        </Col>
        <Col :md="12"></Col>
    </Row>
</div>
</template>

<script>
import GlobalServer from '../../config.js';
import axios from 'axios';
import Cookies from 'js-cookie';
export default {
    name: 'whitelist_list_index',
    data () {
        return {
            dto:{
                householdDto:{
                    id:''
                }
            },
            bigPhotoUrl:'',
            showPictureModal: false,
            progresshow:false,
            progresscount:0,
            progresstatus:'active',
            progressspeed:0,
            household_data:[],
            household_thumb:[],
            household_page_data:[],
            datacount: 0,
            pageindex:1,
            pagesize: 10,
            communityID:[],
            communityName:[],
            searchCommunityID:'',
            searchContent:'',
            communityID_arr:[],
            communityName_arr:[],
            communityList:[],
            page_loading:false,
            data_loading:false,
            columns_title:[
                {
                    title: 'ID',
                    key: 'id',
                    ellipsis: true,
                    width: 80,
                    align: 'center'
                },{
                    title: '小区名称',
                    key: 'communityName',
                    ellipsis: true,
                    width: 100,
                    align: 'center'
                },{
                    title: '缩略图',
                    key: 'photoUrl',
                    width: '120',
                    align: 'center',
                    render: (h,params) => {
                        return h('img',{
                            attrs: {
                                src: this.household_thumb[params.row._index],
                                style: 'width:100%;height:auto;margin-top:4px;'
                            },
                            on: {
                                click: () => {
                                    let rowIndex = params.row._index;
                                    this.bigPhotoUrl = this.household_thumb[rowIndex];
                                    this.showPictureModal = true;
                                }
                            }
                        })
                    }
                },{
                    title: '姓名',
                    key: 'householdName',
                    ellipsis: true,
                    width: 110,
                    align: 'center'
                },{
                    title: '性别',
                    key: 'gender',
                    ellipsis: true,
                    align: 'center',
                    width: 70
                },{
                    title: '身份证号',
                    key: 'identifyCard',
                    width: 150,
                    align: 'center'
                },{
                    title: '联系电话',
                    key: 'tel',
                    ellipsis: true,
                    width: 120,
                    align: 'center'
                },{
                    title: '地址',
                    key: 'roomPath',
                    width: 100,
                    align: 'center'
                },{
                    title: '门禁卡号',
                    key: 'cardNumber',
                    width: 100,
                    align: 'center'
                },{
                    title: '业主类型',
                    key: 'householdType',
                    align: 'center'
                },{
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    ellipsis: true,
                    width: 120,
                    render: (h, params) => {
                        /* const task_status = parseInt(params.row.task_status); */
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        // console.info(params);
                                        let rowIndex = params.row._index;
                                        let photoId = this.household_data[rowIndex].photoId,
                                            address = params.row.roomPath.split('-'),
                                            cardNumber = this.household_data[rowIndex].cardNumber,
                                            remarks = this.household_data[rowIndex].remarks,
                                            communityIndex = this.communityName.indexOf(params.row.communityName);
                                        let argu = {
                                            formdate:{
                                                id: params.row.id,
                                                householdName: params.row.householdName,
                                                gender: params.row.gender,
                                                identifyCard : params.row.identifyCard,
                                                tel: params.row.tel,
                                                communityName: this.communityID[communityIndex],
                                                buildingBlockNumber: address[0],
                                                apartmentNumber: address[1],
                                                floorNumber: address[2],
                                                roomNumber: address[3],
                                                householdType: params.row.householdType,
                                                cardNumber: cardNumber,
                                                remarks: remarks,
                                                photoId: photoId,
                                                photoUrl: params.row.photoUrl,
                                            },
                                            communityList: this.communityList
                                        };
                                        this.$router.push({
                                            name: 'whitelist_edit_index',
                                            params: argu
                                        });
                                    }
                                }
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        /* console.info(params); */
                                        this.remove(params);
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ]
        }//return
    },//data
    /* 这儿开始是定义所有函数的地方 */
    methods: {
        init() {
            let access = Cookies.get('access'),
                obj = {},
                communityIDList = sessionStorage.getItem('communityID').split(','),
                communityNameList = sessionStorage.getItem('communityName').split(',');
                this.communityID = communityIDList;
                this.communityName = communityNameList;
                for(let i=0;i !== communityIDList.length; i++){
                    obj.value =communityIDList[i];
                    obj.label = communityNameList[i];
                    this.communityList.push(obj);
                    obj={};
                }
                if(access === '0') {
                    this.searchCommunityID = -1;
                } else {
                    this.searchCommunityID = communityIDList[0];
                    sessionStorage.setItem('searchCommunityID',this.searchCommunityID);
                }
        },
        getHouseholdDate(currentPage,pageSize) {
            let _this = this;
            let communityID = sessionStorage.getItem('searchCommunityID'),
                searchContent = _this.searchContent;
            _this.page_loading = true;
            /* 获取所有住户信息并将值传入进household_data数组 */
            axios.get(GlobalServer.findAllHouseHold + '?page=' + currentPage + '&pageSize=' + pageSize + '&communityId=' + communityID + '&searchContent=' + searchContent)
            .then(function(response){
                let data = response.data;
                if(data.error === null){
                    /* 将获取到的住户信息数据存入household_data,用以缓存/分页 */
                    _this.household_thumb = [];
                    _this.household_data = data.householdlist.map(function(value){
                        let tempObj = {};
                        tempObj.id = value.id;
                        tempObj.communityName = value.communityName;
                        tempObj.photoUrl = GlobalServer.ServerHost + 'onwing-web/' + value.photoId;
                        tempObj.householdName = value.householdName;
                        tempObj.gender = value.gender;
                        tempObj.identifyCard = value.identifyCard;
                        tempObj.tel = value.tel;
                        tempObj.roomPath = value.roomPath.replace(/\//g,'-').replace(/^\-/,'');
                        tempObj.cardNumber = value.cardNumber;
                        tempObj.householdType = value.householdType;
                        tempObj.remarks = value.remarks;
                        _this.household_thumb.push(GlobalServer.ServerHost + 'onwing-web/' + value.photoId);
                        return tempObj;
                    });

                    _this.datacount = data.totalNumber;

                    /* 执行分页函数将住户信息数据分页,函数的参数为需要显示内容的页数 */
                    //_this.datacount < _this.pagesize ? _this.household_page_data = _this.household_data : _this.household_page_data = _this.household_data.slice(0,_this.pagesize);
                    /* 将page_loading值设置为false,隐藏'下在加载数据'的蒙板 */
                    _this.page_loading = false;
                }
            })
            .catch(function(error){
                console.info('error=' + error);
            })
        },
        remove (params) {
            /* 删除一条住户信息 */
            let _this = this,
                currentPage = _this.$refs.page.currentPage,
                index = params.index,
                indexInHouseholdData = (currentPage-1)*10 + index-1;

            _this.dto.householdDto.id = params.row.id;
            // console.info(_this.dto);
            axios.post(GlobalServer.removeHouseHold,_this.dto)
            .then(function(response){
                // 如果返回值中的error为null，表示删除成功，并将household_data数组中对应的值删除
                if(response.data.error == null){
                    _this.household_data.splice(index, 1);
                    //_this.household_data.splice(indexInHouseholdData, 1);
                    _this.$Message.success('删除成功！');
                }
            })
            .catch(function(error){
                console.info(error);
            })
        },
        changePage(index){
            // start 每页的开始数据
            // let start = (index - 1) * this.pagesize;
            // end 每页的结束数据
            // let end = index * this.pagesize;

            // this.household_page_data = this.household_data.slice(start,end);

            this.getHouseholdDate(index,this.pagesize);
        },
        exportData (type) {
            if (type === 1) {
                this.$refs.table.exportCsv({
                    filename: '原始数据',
                    columns: this.columns_title,
                    data: this.household_data
                });
            } else if (type === 2) {
                this.$refs.table.exportCsv({
                    filename: '排序和过滤后的数据',
                    original: false
                });
            }
        },
        closeModal () {
            this.showPictureModal = false;
        }
    },
    /* 这儿开始是生命周期 */
    beforeCreate() {},
    created() {
        this.init();
        this.getHouseholdDate(this.pageindex,this.pagesize);
    },
    beforeMount() {},
    mounted() {},
    watch:{
        searchContent(arg1){
            if (arg1.length === 0) {
                this.getHouseholdDate(1,this.pagesize);
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

  /*wz-btn wz-btn-primary wz-btn-small wz-btn-loading*/

  .ivu-btn.ivu-btn-primary.ivu-btn-small:not(.ivu-btn-loading) {
    padding: 2px 10px!important;
  }

  td.ivu-table-expanded-cell {
    background-color: white!important;
  }
</style>
