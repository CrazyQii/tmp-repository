<template>
    <div>
        <a-steps>
            <a-step status="finish" title="信息确认">
            <a-icon slot="icon" type="form" />
            </a-step>
            <a-step status="finish" title="待支付">
            <a-icon slot="icon" type="transaction" />
            </a-step>
            <a-step status="finish" title="支付成功">
                <a-icon slot="icon" type="smile-o" />
            </a-step>
        </a-steps>

        <a-page-header
            style="border: 1px solid rgb(235, 237, 240); margin-top: 2rem;"
            title="支付成功"
        />
    </div>
</template>

<script>
export default {
    name: 'PaySuccess',
}
</script>