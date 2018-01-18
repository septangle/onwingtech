<template>
<div class="animated fadeIn">
    <Row>
        <Col :md="24">
            <div>
                <div style="position:relative;">
                    <!-- 表格开始 -->
                    <Table stripe border height="auto" :columns="columns_title" :data="stranger_data" ref="table"></Table>
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
    </Row>
    <!-- 离开时的照片提交框 -->
    <div class="dialog-outFace" v-show="showDialog">
        <span class="close" @click="hideDialog()">X</span>
        <p @click="handleFileSelect()">
          <img :src="outFacePhoto" />
        </p>
        <form style="display: none">
            <input id="photoUp" type="file" accept="image/*" ref="photoUp" @change="handleFileChange()"/>
        </form>
        <p class="tips" style="margin-top: 10px;height: 16px;color:#ff0000;">{{message}}</p>
        <p style="text-align: center;margin-top: 10px;">
            <Button type="primary" @click="handleSubmit()">提交</Button>
        </p>
    </div>
    <div class="mask" v-show="showDialog"></div>
</div>
</template>

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

.dialog-outFace {
    position: absolute;
    width: 360px;
    height: 370px;
    left: 50%;
    top: 10%;
    margin-left: -180px;
    padding: 30px 20px 20px;
    border-radius: 10px;
    background-color: #fff;
    line-height: 1;
    z-index: 100;
}

.dialog-outFace .close {
    position: absolute;
    width: 20px;
    height: 20px;
    cursor: pointer;
    color: #999;
    right: 2px;
    top: 5px;
    font-size: 20px;
    line-height: 1;
}
.dialog-outFace img {
    width: 320px;
    height: 240px;
}

.dialog-outFace .submit {

}
.mask {
    position: absolute;
    width: 100%;
    height: 100%;
    left: 0;
    top: 0;
    background-color: rgba(0,0,0,.5);
}
</style>
<script>
    import axios from 'axios';
    import GlobalServer from '../../config.js';

    export default {
        name: 'guestlist_list_index',
        data () {
            return {
                showDialog: false,
                tipsShow: false,
                identifyCard: '',
                outFacePhoto:'',
                message:'',
                file:'',
                progresshow:false,
                progresscount:0,
                progresstatus:'active',
                progressspeed:0,
                stranger_data:[],
                stranger_page_data:[],
                paramsIndex: null,
                datacount: 0,
                pageindex:1,
                pagesize: 10,
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
                        title: '姓名',
                        key: 'name',
                        ellipsis: true,
                        width: 110,
                        align: 'center'
                    },{
                        title: '性别',
                        key: 'sex',
                        align: 'center',
                        width: 70
                    },{
                        title: '身份证',
                        key: 'identifyCard',
                        width: 120,
                        align: 'center'
                    },
                    {
                        title: '联系电话',
                        key: 'tel',
                        ellipsis: true,
                        width: 120,
                        align: 'center'
                    },{
                        title: '来访事由',
                        key: 'reason',
                        ellipsis: true,
                        width: 300,
                        align: 'center'
                    },{
                        title: '备注',
                        key: 'remarks',
                        ellipsis: true,
                        align: 'center'
                    },{
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        width: 80,
                        render: (h, params) => {
                            /* const task_status = parseInt(params.row.task_status); */
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.paramsIndex = params.index;
                                            this.identifyCard = params.row.identifyCard;
                                            this.showDialog = true;
                                        }
                                    }
                                }, '离开')
                            ]);
                        }
                    }
                ]
            }//return
        },//data
        /* 这儿开始是定义所有函数的地方 */
        methods: {
            getStrangerDate(currentPage,pageSize) {
                /* axios有自己的作用域,无法获取vue实例,所以要将vue实例的this传到一个变量中以便在axios中调用 */
                var _this = this;
                /* 将page_loading值设置为true,用以在获取数据时显示‘正在加载数据’的蒙板 */
                _this.page_loading = true;
                /* 获取所有住户信息并将值传入进household_data数组 */
                axios.get(GlobalServer.getAllStranger + '?page=' + currentPage + '&pageSize=' + pageSize)
                .then(function(response){
                    let data = response.data;
                    if(data.strangerDtoList){
                        /* 将获取到的住户信息数据存入household_data,用以缓存/分页 */
                        _this.stranger_data = data.strangerDtoList;

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
            changePage(index){
                // start 每页的开始数据
                // let start = (index - 1) * this.pagesize;
                // end 每页的结束数据
                // let end = index * this.pagesize;

                //this.household_page_data = this.household_data.slice(start,end);
                this.getStrangerDate(index,this.pagesize)
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
            handleFileSelect() {
                document.getElementById('photoUp').click();
            },
            handleFileChange() {
                var file = this.$refs.photoUp.files[0];
                var reader = new FileReader();
                let _this = this;
                _this.file = file;
                reader.readAsDataURL(file);
                reader.onload = function(event){
                    _this.outFacePhoto = event.target.result;
                    console.info(_this.outFacePhoto);
                };
            },
            handleSubmit() {
                if(this.outFacePhoto.length == 0){
                    this.message = '请上传访客离开时拍摄的照片！';
                    return false;
                } else {
                    var _this = this;
                    let formdata = new FormData();
                    let config = {
                        headers:{
                            'Content-Type':'application/x-www-form-urlencoded'
                        }
                    };
                    formdata.append('file',_this.file);
                    formdata.append('identifyCard',_this.identifyCard);
                    axios.post(GlobalServer.getStrangerLevel,formdata,config)
                    .then(function(response){
                        //console.info(response);
                        let data = response.data;
                        if(data.code == 1) {
                            _this.stranger_data.splice(_this.paramsIndex,1);
                            _this.message = data.message + '点击右上角关闭';
                            //window.setTimeout(_this.hideDialog(),3000);
                        } else {
                            _this.message = data.message;
                        }
                    })
                    .catch(function(error){
                        console.info('error=' + error);
                    })
                  }
            },
            hideDialog () {
                this.showDialog = false;
                this.outFacePhoto = '';
                this.message = '';
                this.file = null;
            }
        },
        /* 这儿开始是生命周期 */
        beforeCreate() {},
        created() {
            this.getStrangerDate(this.pageindex,this.pagesize);
        },
        beforeMount() {},
        mounted() {}
    }
</script>
