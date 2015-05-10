/*jshint node: true */

"use strict";

module.exports = function(grunt) {

    var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;

    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    grunt.initConfig({
        connect: {
            server: {
                options: {
                    hostname: 'localhost',
                    port: '9000',
                    middleware: function() {
                        return [proxySnippet];
                    }
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

    grunt.registerTask('server', [
        'configureProxies:server',
        'connect:server',
    ]);
};