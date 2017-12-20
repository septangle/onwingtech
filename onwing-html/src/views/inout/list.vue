<template>
<div class="animated fadeIn">
    <Row>
        <Col :md="24">
            <div>
                <div style="position:relative;">
                    <!-- 表格开始 -->
                    <Table stripe border height="auto" :columns="columns_title" :data="inout_page_data" ref="table"></Table>
                    <!-- 表格结束 -->

                    <!-- 表格内容加载提示蒙板开始 -->
                    <div style="position:absolute;top:0px;width:100%;height:100%;display: flex;align-items: center;justify-content: center;background: rgba(210, 216, 222, 0.5);" v-if="page_loading">
                        <Spin size="large"></Spin>
                        <h6 style="color:#2d8cf0;margin-top:10px;">正在获取数据...</h6>
                    </div>
                    <!-- 表格内容加载提示蒙板结束 -->
                </div>
                <div style="float:left;margin-top: 20px;">
                <Button type="primary" size="large" @click="exportData(1)"><Icon type="ios-download-outline"></Icon> 导出原始数据</Button>
                </div>
                <!-- 分页开始 -->
                <div style="float:right;margin-top: -30px;">
                <Page :total="datacount" :page-size="pagesize" show-total @on-change="changePage" style="text-align:right;margin-top:50px"></Page>
                </div>
                <!-- 分页结束 -->
            </div>
        </Col>
        <Col :md="12"></Col>
    </Row>
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

;
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
<script>
    import GlobalServer from '../../config.js';
    import axios from 'axios';

    export default {
        name: 'inoutlist_index',
        data () {
            return {
                progresshow:false,
                progresscount:0,
                progresstatus:'active',
                progressspeed:0,
                inout_data:[],
                inout_page_data:[],
                datacount: 0,
                pageindex:1,
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
                        title: '业主姓名',
                        key: 'householdName',
                        ellipsis:'true',
                        width: 110,
                        align: 'center'
                    },{
                        title: '住址',
                        key: 'addres',
                        ellipsis:'true',
                        width: 130,
                        align: 'center'
                    },{
                        title: '进入时间',
                        key: 'outOffTime',
                        align: 'center'
                    }]
            }
        },
        methods: {
            getInoutDate() {
                /* axios有自己的作用域,无法获取vue实例,所以要将vue实例的this传到一个变量中以便在axios中调用 */
                var _this = this;
                /* 将page_loading值设置为true,用以在获取数据时显示‘正在加载数据’的蒙板 */
                _this.page_loading = true;
                /* 获取所有住户信息并将值传入进inout_data数组 */
                axios.get(GlobalServer.findAllAccessRecord)
                .then(function(response){
                    let data = response.data;
                    if(data.error === null){
                        /* 将获取到的住户信息数据存入inout_data,用以缓存/分页 */
                        _this.inout_data = data.houseAccessRecordDtosList;
                        /* 获取数据的条数,并将值赋给vue实例中的datacount */
                        _this.datacount = _this.inout_data.length;
                        /* 如果数据条目数小于每页显示的条目数,则将所有数据传入分页数据对象,
                           如果数据条目数大于每页显示的条目数,则将第一页要显示的数据传入分页数据对象
                        */

                        let tempArr = _this.inout_data;

                        _this.inout_page_data = tempArr.map(function(value){
                            let tempObj = {}
                            let tempDate = _this.setDate(value.outOffTime);
                            tempObj.id = value.id;
                            tempObj.householdName = value.householdName;
                            tempObj.addres = value.buildingBlockNumber + '单元' + value.roomNumber + '室';
                            tempObj.outOffTime = tempDate;
                            console.info(tempObj);
                            return tempObj;
                        });
                        
                        console.info(_this.inout_page_data)
                        /* if (_this.datacount < _this.pagesize) {
                            _this.inout_page_data = _this.inout_data;
                        } else {
                            _this.inout_page_data = _this.inout_data.slice(0,_this.pagesize)
                        } */
                        /* 将page_loading值设置为false,隐藏'下在加载数据'的蒙板 */
                        _this.page_loading = false;
                    }
                })
                .catch(function(error){
                    console.info('error=' + error);
                })
            },
            changePage(index){
                /* start 每页的开始数据 */
                let start = (index - 1) * this.pagesize;
                /* end 每页的结束数据 */
                let end = index * this.pagesize;

                
                this.inout_page_data = this.inout_data.slice(start,end);
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
            }
        },
        beforeCreate() {},
        created() {
            this.getInoutDate();
        },
        beforeMount() {},
        mounted(){
            // const vue=this;
            // 将list_loadding的值变更为true,显示'内容加载提示蒙板'
            // this.list_loadding=true;
            // 2秒后将list_loadding的值变更为false,隐藏'内容加载提示蒙板'
            /* setTimeout(function(){
                vue.list_loadding=false;
            },2000) */
            //this.setInitPage(1);
            /* this.getInoutDate(); */
        },
        activated() {}
    }
</script>