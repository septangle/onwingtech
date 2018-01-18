<template>
<div class="animated fadeIn">
    <Row>
        <Col :md="24">
            <div>
                <div style="position:relative;">
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
    <div class="dialog-showBigPhoto" v-show="showDialog">
      <span class="close" @click="hideDialog">X</span>
      <p>
        <img :src="bigPhotoUrl" />
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

.ivu-btn.ivu-btn-primary.ivu-btn-small:not(.ivu-btn-loading) {
    padding: 2px 10px!important;
}

td.ivu-table-expanded-cell {
    background-color: white!important;
}

.dialog-showBigPhoto {
    position: absolute;
    width: 70%;
    left: 50%;
    top: 10%;
    margin-left: -35%;
    padding: 30px 20px 20px;
    border-radius: 10px;
    background-color: #fff;
    line-height: 1;
    z-index: 100;
}

.dialog-showBigPhoto .close {
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
.dialog-showBigPhoto img {
    width: 100%;
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
    import GlobalServer from '../../config.js';
    import axios from 'axios';

    export default {
        name: 'guestlist_inout_index',
        data () {
            return {
                showDialog: false,
                bigPhotoUrl:'',
                progresshow:false,
                progresscount:0,
                progresstatus:'active',
                progressspeed:0,
                inout_data:[],
                inout_page_data:[],
                inout_thumb: [],
                datacount: 0,
                pageindex: 1,
                pagesize: 20,
                page_loading:false,
                data_loading:false,
                columns_title:[
                    {
                        title: 'ID',
                        key: 'id',
                        ellipsis:'true',
                        width: 80,
                        align: 'center'
                    },{
                        title: '缩略图',
                        key: 'miniPhoto',
                        width: '100',
                        align: 'center',
                        render: (h,params) => {
                            return h('img',{
                              attrs: {
                                src: this.inout_thumb[params.row._index],
                                style: 'width: 80px'
                              },
                              on: {
                                  click: (event) => {
                                      this.bigPhotoUrl = event.target.src;
                                      this.showDialog = true;
                                      console.info(this);
                                  }
                              }
                            })
                        }
                    },{
                        title: '状态',
                        key: 'outOffInto',
                        width: 110,
                        align: 'center'
                    },{
                        title: '姓名',
                        key: 'name',
                        ellipsis:'true',
                        width: 200,
                        align: 'center'
                    },{
                        title: '时间',
                        key: 'time',
                        align: 'center'
                    }]
            }
        },
        methods: {
            getInoutDate(currentPage,pageSize) {
                /* axios有自己的作用域,无法获取vue实例,所以要将vue实例的this传到一个变量中以便在axios中调用 */
                var _this = this;
                /* 将page_loading值设置为true,用以在获取数据时显示‘正在加载数据’的蒙板 */
                _this.page_loading = true;
                /* 获取所有住户信息并将值传入进inout_data数组 */
                axios.get(GlobalServer.findAllStrangerAccessRecord + '?page=' + currentPage + '&pageSize=' + pageSize)
                .then(function(response){
                    let data = response.data;
                    if(data.error === null){
                        /* 获取数据的条数,并将值赋给实例中的datacount */
                        _this.datacount = data.totalNumber;

                        /* 定义tempArr数组，临时存放返回的出入记录 */
                        let tempArr = data.accessRecordMapDtosList;

                        /* 遍历tempArr数组，将数组中每一条出入记录对象中的单元号和房号进行拼接 */
                        _this.inout_data = tempArr.map(function(value){
                            let tempObj = {};
                            // let tempDate = _this.setDate(value.time);

                            tempObj.id = value.id;
                            tempObj.name = value.name;
                            tempObj.time = _this.setDate(value.time);
                            tempObj.outOffInto = value.outOffInto == '0' ? '入' : '出';
                            _this.inout_thumb.push(GlobalServer.ServerHost + 'onwing-web/' + value.photoUrl);
                            return tempObj;
                        });

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
                });
                //console.info(_this.inout_thumb);
            },
            changePage(index){  //入参index <Page>组件中on-change事件的返回值，表示页码改变后的页码
                // start 每页的开始数据
                // let start = (index - 1) * this.pagesize;
                // end 每页的结束数据
                // let end = index * this.pagesize;

                // this.inout_page_data = this.inout_data.slice(start,end);

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
                    month = temp.getMonth() + '月',
                    date = temp.getDate() + '日',
                    hours = temp.getHours() + '点',
                    minutes = temp.getMinutes() + '分',
                    second = temp.getSeconds() + '秒',
                    fullDate = '';
                return fullDate = year + month + date + hours + minutes + second;
            },
            hideDialog () {
                this.showDialog = false;
                this.bigPhotoUrl = '';
            }
        },
        beforeCreate() {},
        created() {
            this.getInoutDate(this.pageindex,this.pagesize);
            //console.info(this.inout_thumb);
        },
        beforeMount() {},
        mounted() {},
        activated() {}
    }
</script>
