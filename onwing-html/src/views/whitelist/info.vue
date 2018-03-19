<template>
    <div class="validateMain">
        <div class="photoView">
            <img :src="householdInfo.photoUrl" ref="img">
        </div>
        <div class="validateView">
            <Form ref="householdInfo" :model="householdInfo" :rules="ruleValidate" :label-width="100" class="formstyle">
                <FormItem label="姓名" prop="householdName">
                    <Input v-model="householdInfo.householdName" disabled></Input>
                </FormItem>
                <FormItem label="性别" prop="gender">
                    <RadioGroup v-model="householdInfo.gender">
                        <Radio label="男" disabled>男</Radio>
                        <Radio label="女" disabled>女</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="身份证" prop="identifyCard">
                    <Input v-model="householdInfo.identifyCard" disabled></Input>
                </FormItem>
                <FormItem label="联系电话" prop="tel">
                    <Input v-model="householdInfo.tel" disabled></Input>
                </FormItem>
                <FormItem label="小区名称" prop="communityName">
                    <Input v-model="householdInfo.communityName" disabled></Input>
                </FormItem>
                <FormItem label="楼号" prop="buildingblockNumber">
                    <Input v-model="householdInfo.buildingBlockNumber" disabled></Input>
                </FormItem>
                <FormItem label="单元号" prop="apartmentNumber">
                    <Input v-model="householdInfo.apartmentNumber" disabled></Input>
                </FormItem>
                <FormItem label="楼层" prop="floorNumber">
                    <Input v-model="householdInfo.floorNumber" disabled></Input>
                </FormItem>
                <FormItem label="房号" prop="roomNumber">
                    <Input v-model="householdInfo.roomNumber" disabled></Input>
                </FormItem>
                <FormItem label="业主类型" prop="householdType">
                    <RadioGroup v-model="householdInfo.householdType">
                        <Radio label="住户" disabled>住户</Radio>
                        <Radio label="租客" disabled>租客</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="门禁卡号" prop="cardNumber">
                    <Input v-model="householdInfo.cardNumber" disabled></Input>
                </FormItem>
                <FormItem label="备注" prop="remarks">
                    <Input v-model="householdInfo.remarks" type="textarea" :autosize="{minRows: 2,maxRows: 5}" disabled></Input>
                </FormItem>
            </Form>
        </div>
    </div>
</template>
<script>
import GlobalServer from '../../config.js';
import axios from 'axios';
import Cookies from 'js-cookie';
export default {
    name:'whitelist_info_index',
    data () {
        return {
            householdInfo: {
                id: '',
                householdName: '',
                gender: '',
                identifyCard:'',
                tel: '',
                communityName:'',
                buildingBlockNumber: '',
                apartmentNumber:'',
                floorNumber:'',
                roomNumber: '',
                householdType:'',
                cardNumber: '',
                remarks: '',
                photoId: '',
                photoUrl:'',
            },
        }
    },
    methods: {
        init() {
            let data = this.$route.params,
                dto = {
                    householdDto:{
                      id : data.householdID
                    }
                };
            console.info(data);
            if(data.pageID === 'inout'){
                let _this = this;

                axios.post(GlobalServer.findHouseHoldById,dto)
                    .then(function(response){
                        let data = response.data,
                            address;
                        address = data.houseHoldDto.roomPath.split('/');
                        _this.householdInfo.householdName = data.houseHoldDto.householdName;
                        _this.householdInfo.gender = data.houseHoldDto.gender;
                        _this.householdInfo.identifyCard = data.houseHoldDto.identifyCard;
                        _this.householdInfo.tel = data.houseHoldDto.tel;
                        _this.householdInfo.communityName = data.houseHoldDto.communityName;
                        _this.householdInfo.buildingBlockNumber = address[1];
                        _this.householdInfo.apartmentNumber = address[2];
                        _this.householdInfo.floorNumber = address[3];
                        _this.householdInfo.roomNumber = address[4];
                        _this.householdInfo.householdType = data.houseHoldDto.householdType;
                        _this.householdInfo.cardNumber = data.houseHoldDto.cardNumber;
                        _this.householdInfo.remarks = data.houseHoldDto.remarks;
                    })
                    .catch(function(err){
                        console.info(err);
                    })
            } else {
                this.householdInfo = data;
                console.info(this.householdInfo);
            }
        },

    },
    created(){
        this.init();
    },
    activated () {
        this.init();
    },
    watch: {
        '$route' () {
            this.init();
        }
    }
}
</script>
<style type="text/css" scoped>
  .validateMain {

  }
  .validateMain:before,
  .validateMain::after {
    content: '';
    display: table;
    clear: both;
  }
  .photoView {
    float: left;
    width: 214px;
    /*height: 100px;*/
  }
  .photoView img {
    width: 100%;
  }
  .validateView {
    /*margin-left: 20px;*/
    float: left;
    width: 30%;
  }
  .validateView input[disabled] {
    background-color: #fff;
    color: #333;
  }
</style>
