var nations = {
    "A": {
        "39":["阿昌族"]
    },
    "B": {
        "14": ["白族"],
        "09": ["布依族"],
        "34": ["布朗族"],
        "47": ["保安族"]
    },
    "C": {
        "10":["朝鲜族"]
    },
    "D": {
        "12":["侗族"],
        "18":["傣族"],
        "26":["东乡族"],
        "46":["崩龙族"],
        "51":["独龙族"],
        "31":["达斡尔族"]
    },
    "E": {
        "44":["俄罗斯族"],
        "45":["鄂温克族"],
        "52":["鄂伦春族"]
    },
    "G": {
        "23":["高山族"],
        "37":["仡佬族"]
    },
    "H": {
        "17":["哈萨克族"],
        "16":["哈尼族"],
        "53":["赫哲族"]
    },
    "J": {
        "28":["景颇族"],
        "49":["京族"],
        "56":["基诺族"]
    },
    "K": {
        "29":["柯尔克孜族"]
    },
    "L": {
        "19":["黎族"],
        /*
         "23":["傈僳族"],
         */
        "24":["拉祜族"],
        "55":["珞巴族"]
    },
    "M": {
        "06":["苗族"],
        "02":["蒙古族"],
        "11":["满族"],
        "32":["仫佬族"],
        "36":["毛南族"],
        "54":["门巴族"]
    },
    "N": {
        "27":["纳西族"],
        "42":["怒族"]
    },
    "H": {
        "01":["汉族"],
        "03":["回族"]
    },
    "P": {
        "40":["普米族"]
    },
    "Q": {
        "33":["羌族"]
    },
    "S": {
        "22":["畲族"],
        "25":["水族"],
        "35":["撒拉族"]
    },
    "T": {
        "15":["土家族"],
        /*
         "23":["土家"],
         */
        "41":["塔吉克族"],
        "50":["塔塔尔族"]
    },
    "W": {
        "05":["维吾尔族"],
        "21":["佤族"],
        "43":["乌孜别克族"]
    },
    "X": {
        "38":["锡伯族"]
    },
    "Y": {
        "13":["瑶族"],
        "07":["彝族"],
        "48":["裕固族"]
    },
    "Z": {
        "04":["藏族"],
        "08":["壮族"]
    }
};

(function ($, win, doc) {
    var NationPicker = function (el, options, fun) {
        this.el = $(el);
        this.options = options;
        this.nations = nations;
        this.nationCode = null;
        this.nationName = null;
        this.fun = fun || null;
        this.elType = this.el.is('input');
        this.event = this.el.is('input') ? 'focus' : 'click';
        this.hiddenList = {};
        this.init();
    };

    var p = NationPicker.prototype;

    p.init = function () {
        this.initEvent();
        this.preventPopKeyboard();

    };

    p.preventPopKeyboard = function () {
        if (this.elType) {
            this.el.prop("readonly", true);
        }
    };

    p.initEvent = function () {
        this.el.on(this.event, function (e) {
            var pickerBox = $(".picker-box-nation");
            if (pickerBox[0]) {
                pickerBox.show();
            } else {
                this.create();
            }
        }.bind(this));
    };

    p.create = function () {
        this.createNationPickerBox();
        this.createNationList();
        this.nationClick();
        this.createNavBar();
        this.navEvent();
    };

    p.createNationPickerBox = function () {
        var proBox = "<div class='picker-box-nation'></div>";
        $("body").append(proBox);
    };

    p.createNationList = function () {
        var nations = this.nations;
        var nationBox;
        var dl = "";
        for (var letterKey in nations) {
            var val = nations[letterKey];
            if (nations.hasOwnProperty(letterKey)) {
                var dt = "<dt id='" + letterKey + "'>" + letterKey + "</dt>";
                var dd = "";
                for (var key in val) {
                    if (val.hasOwnProperty(key)) {
                        dd += "<dd data-letter=" + letterKey + " data-code=" + key + ">" + val[key][0] + "</dd>";
                    }
                }
                dl += "<dl>" + dt + dd + "</dl>";
            }
        }

        nationBox = "<section class='nation-picker'>" + dl + "</section>";

        $(".picker-box-nation").append(nationBox);
    };


    p.nationClick = function () {
        var that = this;
        $(".nation-picker").on("click", function (e) {
            var target = e.target;
            if ($(target).is("dd")) {
                that.nationName = $(target).html();
                that.nationCode = $(target).data("code");

                if (that.elType) {
                    that.el.val(that.nationName);
                } else {
                    that.el.html(that.nationName);
                }

                $(".picker-box-nation").hide();
                $.extend(true, that.hiddenList, {
                    nationName: that.nationName,
                    nationCode: that.nationCode
                });

                if (that.fun) that.fun.call(that, that.hiddenList);
            }
        });
    };


    p.createNavBar = function () {
        //var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var str = "ABCDEGHJKLMNHPQSTWXYZ";
        var arr = str.split("");
        var a = "";
        arr.forEach(function (item, i) {
            a += '<a href="#' + item + '">' + item + '</a>';
        });

        var div = '<div class="navbar">' + a + '</div>';

        $(".picker-box-nation").append(div);
    };

    p.navEvent = function () {
        var that = this;
        var navBar = $(".navbar");
        var width = navBar.find("a").width();
        var height = navBar.find("a").height();
        navBar.on("touchstart", function (e) {
            $(this).addClass("active");
            that.createLetterPrompt($(e.target).html());
        });

        navBar.on("touchmove", function (e) {
            e.preventDefault();
            var touch = e.originalEvent.touches[0];
            var pos = {"x": touch.pageX, "y": touch.pageY};
            var x = pos.x, y = pos.y;
            $(this).find("a").each(function (i, item) {
                var offset = $(item).offset();
                var left = offset.left, top = offset.top;
                if (x > left && x < (left + width) && y > top && y < (top + height)) {
                    location.href = item.href;
                    that.changeLetter($(item).html());
                }
            });
        });

        navBar.on("touchend", function () {
            $(this).removeClass("active");
            //$(".prompt").hide();
            setTimeout(function() {
                $(".prompt").remove();
            }, 150)
        })
    };

    p.createLetterPrompt = function (letter) {
        var prompt = $(".prompt");
        if (prompt[0]) {
            prompt.show();
        } else {
            var span = "<span class='prompt'>" + letter + "</span>";
            $(".picker-box-nation").append(span);
        }
    };


    p.changeLetter = function (letter) {
        var prompt = $(".prompt");
        prompt.html(letter);
    };

    $.fn.nationPicker = function (options, fun) {
        return new NationPicker(this, options, fun);
    }
}(window.jQuery, window, document));