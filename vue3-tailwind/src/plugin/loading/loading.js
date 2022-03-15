import { reactive, h, createApp } from "vue";
import "./loading.css";

export default {
  install: (app, options) => {
    const state = reactive({
      loading: false
    });

    let dom = document.querySelector("#my-loading-container");
    if (!dom) {
      console.log("start first loading");
      const loadingContainer = createApp({
        render() {
          return h(
            "div",
            {
              class: ["loading_own", state.loading ? "" : "display_none"].join(
                " "
              )
            },
            [
              h("span", {}, []),
              h("span", {}, []),
              h("span", {}, []),
              h("span", {}, []),
              h("span", {}, [])
            ]
          );
        }
      });
      let containerDom = document.createElement("div");
      containerDom.id = "my-loading-container";
      containerDom.className = "loading_full"
      document.body.appendChild(containerDom);
      loadingContainer.mount("#my-loading-container");
    } else {
      console.log("exist loading container");
    }

    const openLoading = () => {
      console.log("open loading");
      state.loading = true;
    };

    const closeLoading = () => {
      console.log("close loading");
      state.loading = false;
    };

    app.config.globalProperties.$my_loading = (flag) => {
      if (flag) {
        openLoading();
      } else {
        closeLoading();
      }
    };
  }
};
