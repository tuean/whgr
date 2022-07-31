<template>
    <div v-html="markdown"></div>
</template>


<script>
import { marked } from 'marked';

export default {
    setup() {
        const options = {sanitize: true}
        const markdown = marked(`# whgr`, options)
        return {
            markdown
        }
    }
}
</script>