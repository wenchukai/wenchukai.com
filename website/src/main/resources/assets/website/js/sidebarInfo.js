/**
 * 侧边栏信息，依赖jquery
 */

define(["lscache"], function (lscache) {
    return {
        init: function () {
            function showSideBarInfo(data) {
                var vm = new Vue({
                    data: data,
                    el: "#sidebar-div",
                    methods: {
                        yearMonth: function (item) {
                            return item.yearMonth.year + '-' + item.yearMonth.monthValue;
                        },
                        defaultPic: function (pic) {
                            return pic || '//static.chulung.com/group1/M00/00/00/cHx_F1b31x6ASf2iAAAfnIyLLQI109_150x150.jpg';
                        }
                    }
                });
                var colophonVm=new Vue({
                    el:"#colophon",
                    data:data
                });
            }

            if (lscache.get("sidebarInfo")) {
                showSideBarInfo(lscache.get("sidebarInfo"));
            } else {
                $.ajax({
                    type: "GET",
                    url: "/sidebarInfo",
                    dataType: "json",
                    success: function (data) {
                        showSideBarInfo(data);
                        lscache.set("sidebarInfo", data, 1440)
                    }
                });
            }
        }
    }
});