var serverlessSDK = require('./serverless_sdk/index.js')
serverlessSDK = new serverlessSDK({
tenantId: 'abhackathon',
applicationName: 'culture-dev-app',
appUid: 'CnJlcP3ngLW1dpWjH4',
tenantUid: '3vrrXfdqNhtQtyBbcz',
deploymentUid: '5e3753ca-fbf4-495b-a291-70d767d45a8e',
serviceName: 'culture-dev',
stageName: 'app',
pluginVersion: '2.0.0'})
const handlerWrapperArgs = { functionName: 'culture-dev-app-nuxt', timeout: 6}
try {
  const userHandler = require('./index.js')
  module.exports.handler = serverlessSDK.handler(userHandler.nuxt, handlerWrapperArgs)
} catch (error) {
  module.exports.handler = serverlessSDK.handler(() => { throw error }, handlerWrapperArgs)
}
