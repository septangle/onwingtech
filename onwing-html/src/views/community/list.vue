<template>
  <div class="animated fadeIn">
    <Row>
      <Col :md="24">
      <div>
        <div style="position:relative;">
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
  import axios from 'axios';
  import GlobalServer from '../../config.js';
  import Cookies from 'js-cookie';
  export default {
    name: 'community_list_index',
    data () {
      return {
        dto:{
          householdDto:{
            id:''
          }
        },
        baseUrl: '',
        progresshow:false,
        progresscount:0,
        progresstatus:'active',
        progressspeed:0,
        household_data:[],
        household_page_data:[],
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
            key: 'doorControNumber',
            ellipsis: true,
            width: 150,
            align: 'center'
          },{
            title: '摄像头设备数',
            key: 'roomNumber',
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
              /* const task_status = parseInt(params.row.task_status); */
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      /* console.info(params); */
                      this.remove(params);
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
      getHouseholdDate(currentPage,pageSize) {
        /* axios有自己的作用域,无法获取vue实例,所以要将vue实例的this传到一个变量中以便在axios中调用 */
        var _this = this;
        /* 将page_loading值设置为true,用以在获取数据时显示‘正在加载数据’的蒙板 */
        _this.page_loading = true;
        /* 获取所有住户信息并将值传入进household_data数组 */
        axios.get(GlobalServer.findAllHouseHold + '?page=' + currentPage + '&pageSize=' + pageSize)
          .then(function(response){
            let data = response.data;
            if(data.householdlist){
              /* 将获取到的住户信息数据存入household_data,用以缓存/分页 */
              _this.household_data = data.householdlist;

              _this.datacount = data.totalNumber;

              /* 执行分页函数将住户信息数据分页,函数的参数为需要显示内容的页数 */
              //_this.datacount < _this.pagesize ? _this.household_page_data = _this.household_data : _this.household_page_data = _this.household_data.slice(0,_this.pagesize);
              /* 将page_loading值设置为false,隐藏'下在加载数据'的蒙板 */
              _this.page_loading = false;
            }
            /* console.info(_this.household_data); */
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
      }
    },
    /* 这儿开始是生命周期 */
    beforeCreate() {},
    created() {
      this.getHouseholdDate(this.pageindex,this.pagesize);
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
