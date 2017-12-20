<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="username">
                            <Input v-model="form.username" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import GlobalServer from '../config.js';
import axios from 'axios';
export default {
    data () {
        return {
            form: {
                username: '',
                password: ''
            },
            respData:{},
            rules: {
                username: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleSubmit () {
            let dto = {
                adminiStratorDto:{}
            };
            let _this = this;
            _this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    dto.adminiStratorDto.adminName = _this.form.username;
                    dto.adminiStratorDto.password = _this.form.password;
                    axios.post(GlobalServer.login,dto)
                    .then(function(response){
                        let data = response.data;
                        if (data.error === null) {
                            let adminName = data.adminiStratorDto.adminName
                            Cookies.set('user', adminName);
                            Cookies.set('access',0);
                            _this.$Message.success('登录成功！');
                            setTimeout(function(){
                                _this.$router.push({
                                    name: 'home_index'
                                });
                            },2000);
                        }
                        //_this.respData.adminName = 
                        /* if(response){
                            console.info(data.error.length);
                            let data = response.data;
                            if(data.error.code === '10020001'){
                                console.info('error');
                                _this.$Message.error('帐号或密码错误！');
                            }
                            console.info('true');
                            _this.respData = data.adminiStratorDto;
                        } */
                        //Cookies.set('user', _this.respData.adminName);
                    })
                    .catch(function(error){

                    });

                    _this.$store.commit('setAvator', '../images/avatars-admin.png');


                }
            });
        }
    }
};
</script>

<style>

</style>
