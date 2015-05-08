module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        connect: {
            server: {
                options: {
                    port: 9000,
                    hostname: 'localhost',
                    keepalive: true
                },
                proxies: [
                    {
                        context: '/rest',
                        host: 'localhost',
                        port: 8080
                    }
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-connect-proxy');

    grunt.registerTask('server', function (target) {
        grunt.task.run([
            'configureProxies:server',
            'connect'
        ]);
    });
};