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
                @keyup.enter="submitForm"
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
import VConsole from 'vconsole';

export default {
  setup() {
    window.onresize = () => {
      console.log('height: ', window.innerHeight)
    }
    const vConsole = new VConsole();
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
            router.push({path: '/admin'})
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
  // background-image: url(https://i.loli.net/2019/10/23/rGVfgibcAOs49EM.jpg);
  background-image: url(https://image-1256217908.cos.ap-shanghai.myqcloud.com/%E6%9C%88%E8%A7%81%20%20%E5%A5%B3%E5%AD%A9%E5%AD%90%20%E6%98%9F%E7%A9%BA%20%E6%B5%81%E6%98%9F%20%E9%A4%90%E6%A1%8C%20%E8%81%9A%E4%BC%9A%204k%E5%8A%A8%E6%BC%AB%E5%A3%81%E7%BA%B8_%E5%BD%BC%E5%B2%B8%E5%9B%BE%E7%BD%91.jpg);
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
