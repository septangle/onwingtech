<template>
  <div class="animated fadeIn">
    <Row>
      <Col :md="24">
      <div>
        <div style="position:relative;">
          <!-- 表格开始 -->
          <Table stripe border height="auto" :columns="columns_title" :data="community_page_data" ref="table"></Table>
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
        <div style="display: none">
            <input type="file" accept="" ref="upCsv" @change="fileChange()">
        </div>
      </div>
      </Col>
      <Col :md="12"></Col>
    </Row>
  </div>
</template>

<script>
    import axios from 'axios';
    import GlobalServer from '../../config.js';
    import Cookies from 'js-cookie';
    export default {
        name: 'community_list_index',
        data () {
            return {
                progresshow:false,
                progresscount:0,
                progresstatus:'active',
                progressspeed:0,
                community_data:[],
                community_page_data:[],
                datacount: 0,
                pageindex: 1,
                pagesize: 20,
                page_loading:false,
                data_loading:false,
                file:'',
                communityID:'',
                communityName:'',
                communityIndex:'',
                columns_title:[
                    {
                        title: 'ID',
                        key: 'communityID',
                        ellipsis: true,
                        width: 80,
                        align: 'center'
                    },{
                        title: '小区名称',
                        key: 'communityName',
                        ellipsis: true,
                        width: 200,
                        align: 'center'
                    },{
                        title: '小区地址',
                        key: 'communityAddress',
                        align: 'center'
                    },{
                        title: '门禁设备数',
                        key: 'controNumber',
                        ellipsis: true,
                        width: 150,
                        align: 'center'
                    },{
                        title: '摄像头设备数',
                        key: 'cameraNumber',
                        ellipsis: true,
                        width: 150,
                        align: 'center'
                    },{
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        ellipsis: true,
                        width: 200,
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            /* console.info(params); */
                                            this.doInput(params);
                                        }
                                    }
                                }, '上传拓扑文件')
                            ]);
                        }
                    }//
                ]
            }//return
        },//data
      /* 这儿开始是定义所有函数的地方 */
        methods: {
            getCommunityDate(currentPage,pageSize) {
              let communityName = sessionStorage.getItem('communityName').split(','),
                  communityID = sessionStorage.getItem('communityID').split(','),
                  communityAddress = sessionStorage.getItem('communityAddress').split(','),
                  cameraNumber = sessionStorage.getItem('cameraNumber').split(','),
                  controNumber = sessionStorage.getItem('controNumber').split(','),
                  obj = {};
              this.page_loading = true;
              this.datacount = communityID.length;
              for (let i = 0;i !== this.datacount; i++){
                  obj.communityID = communityID[i];
                  obj.communityName = communityName[i];
                  obj.communityAddress = communityAddress[i];
                  obj.cameraNumber = cameraNumber[i];
                  obj.controNumber = controNumber[i];
                  this.community_data.push(obj);
                  obj = {};
              }
              console.info(this.community_data);
              this.datacount < this.pagesize ? this.community_page_data = this.community_data : this.community_page_data = this.community_data.slice(0,this.pagesize);
              this.page_loading = false;
            },
            doInput (params) {
                console.info(params);
                this.$refs.upCsv.accept = '.csv';
                this.$refs.upCsv.click();
                this.communityIndex = params.row.communityID;
                console.info(this.communityIndex);
            },
            fileChange () {
                console.info('change');
                console.info(this.$refs.upCsv);
                this.file = this.$refs.upCsv.files[0];
                //console.info(this.file);
                this.upFile();
            },
            upFile () {
                console.info('up');
                let _this = this,
                    formdata = new FormData(),
                    config = {
                        headers:{
                          'Content-Type':'application/x-www-form-urlencoded'
                        }
                    };
                console.info('community:' + _this.communityIndex);
                formdata.append('file',_this.file);
                formdata.append('communityId',_this.communityIndex);
                axios.post(GlobalServer.importRoomsFromCvs,formdata,config)
                    .then(function(response){
                        let data = response.data;
                            if(data.error === null) {
                                _this.$Message.success('上传成功！');
                                _this.file = '';
                                _this.communityIndex = '';
                            }
                    })
                    .catch(function(error){
                        console.info(error);
                    });
            },
            changePage(index){
                // start 每页的开始数据
                let start = (index - 1) * this.pagesize;
                // end 每页的结束数据
                let end = index * this.pagesize;

                this.community_page_data = this.community_data.slice(start,end);

                //this.getCommunityDate(index,this.pagesize);
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
            }
        },
      /* 这儿开始是生命周期 */
        beforeCreate() {},
        created() {
            let access = Cookies.get('access');

            if (access === '0') {
                this.communityID = -1;
            } else {
                this.communityID = sessionStorage.getItem('communityID');
            }
            this.getCommunityDate(this.pageindex,this.pagesize);

        },
        beforeMount() {},
        mounted() {}
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
