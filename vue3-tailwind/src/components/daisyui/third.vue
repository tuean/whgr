<template>
    <div>
        <iframe :src="src" frameborder="0" style="width: 100%; height: 100%;"></iframe>
    </div>
</template>

<script>
export default {
    props: {
        src: {
            type: String,
            default: ''
        }
    },
    setup(props) {
        return {
            src: props.src
        }
    }
}
</script>
