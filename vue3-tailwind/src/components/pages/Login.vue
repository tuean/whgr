<template>
  <div class="bg h-screen bg-fixed w-full relative bg-center bg-cover flex">
    <!-- 登录模块 -->
    <el-container
        class="center bg-center w-80 mx-auto items-center justify-items-center justify-center"
    >
      <div
          class="no-select can-click w-80 h-login items-center h-14 font-bold text-3xl text-gray-300 text-center"
      >
        <div>tailwind demo</div>
        <el-form

            label-width="auto"
            :rules="rules"
            :model="ruleForm"
            ref="loginForm"
            class="login-form my-6"
        >
          <el-form-item prop="username">
              <el-input
                  placeholder="账号"
                  type="text"
                  v-model.trim="ruleForm.username"
                  autocomplete="off"
                  class="input"
              ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                placeholder="密码"
                type="password"
                v-model.trim="ruleForm.password"
                autocomplete="off"
                class="input"
                :show-password="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="w-full" type="primary" @click="submitForm"
            >立即登录
            </el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </el-container>
  </div>
</template>

<script>
import {reactive, ref, toRefs} from "vue";
import {get, post} from '../../util/axios'
import md5 from 'js-md5'
import { localSet } from '/@/util/index'
import router from '/@/router/index'



export default {
  setup() {
    const loginForm = ref(null);
    const state = reactive({
      ruleForm: {
        username: '',
        password: ''
      },
      checked: true,
      rules: {
        username: [
          { required: 'true', message: '账户不能为空', trigger: 'blur' }
        ],
        password: [
          { required: 'true', message: '密码不能为空', trigger: 'blur' }
        ]
      }
    })

    const submitForm = async () => {
      loginForm.value.validate((valid) => {
        if (valid) {
          post('/adminUser/login', {
            userName: state.ruleForm.username || '',
            passwordMd5: md5(state.ruleForm.password)
          }).then(res => {
            localSet('token', res.token)
            router.push({path: '/'})
          }).catch(err => {
            console.log(err)
          })
        } else {
          console.log('login error!')
          ElMessage.error('login error!')
          return false;
        }
      })
    }


    return {
      ...toRefs(state),
      loginForm,
      submitForm
    };
  },
};
</script>

<style scpoed lang="less">
.bg {
  background-image: url(https://i.loli.net/2019/10/23/rGVfgibcAOs49EM.jpg);
}

.login-input__inner {
  color: white;
  background-color: skyblue;
  border: 0px;
}
.el-form-item__label{
  font-size: 18px;
  font-weight: bolder;
  color: rgba(209, 213, 219, var(--tw-text-opacity));

}
</style>
