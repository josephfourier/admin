
;layui.define(['jquery', 'element'], function(exports) {
	var $ = layui.jquery
	, element = layui.element
	, doc = $(document)
	, win = $(window)
	, tab = function() {
		this.config = {
			elem: void 0,
			main_url: 'index.html'
		},
		this.version = '1.0'
	};
	//配置参数
	(tab.fn = tab.prototype).set = function(opts) {
		var _self = this;
		return $.extend(!0, _self.config, opts), _self;
	}
	
	tab.fn.render = function(args) {
		var dft = {
			init: false,
			title: false,
			tools: false
		};
		var _self = this
		, cfg = $.extend(!0, _self.config, $.extend(dft, args));
		return void 0 == cfg.elem ? 
				(layui.hint().error('please configure the container of tabs!'), _self) 
				: (TAB._config = cfg, TAB.create_tab_dom(), _self);
	}
	
	tab.fn.add_tab = function(opts) {
		TAB.add_tab(opts);
	}
	
	var TAB = {
		_config: {},
		_filter: 'lap-tab',
		_title: void 0,
		_content: void 0,
		_parent_elem: void 0,
		
		tab_dom_exists: function() {
			var _self  = this;
			return doc.find('div.lap-tab').length > 0
				&& (_self._title = $('.lap-tab ul.layui-tab-title')
					, _self._content = $('.lap-tab div.layui-tab-content'), !0);
			
		},
		
		create_tab_dom: function() {
			var _self = this
			, cfg = _self._config;
			if (_self._parent_elem = cfg.elem, _self.tab_dom_exists()) {
				var a = ['<div class="layui-tab layui-tab-card lap-tab" lay-filter="' + _self._filter + '">'
					/*	,
						!0 == cfg.title ?
						'<ul class="layui-tab-title">' : '<ul class="layui-tab-title lap-hide">'
						,
						'<li class="layui-this" lay-id=-"1"><i class="layui-icon">&#xe68e;</i> 控制面板</li>',
						"</ul>",
						!0 == cfg.tools ?
						'<div class="lap-tab-tool">操作&nbsp;<i class="fa fa-caret-down"></i></div>'
						+'<div class="lap-tab-tool-body layui-anim layui-anim-upbit">'
						+'<ul>'
						+'<li class="lap-item" data-target="refresh">刷新当前选项卡</li>'
						+'<li class="lap-separator"></li>'
						+'</ul>' : ''
						,
						'</div>',
						
						'<div class="layui-tab-content">'
						,
						!0 == cfg.init ?
						'<div class="layui-tab-item layui-show" lay-item-id="-1"><iframe src="' + cfg.main_url +'"></iframe></div>'
						: ''*/
						,
						'<div class="layui-tab-content">'
						,
						'</div>',
						'</div>'
					];
				$(_self._parent_elem).html(a.join(""))
				, _self._title = $('.lap-tab ul.layui-tab-title')
				, _self._content = $('.lap-tab div.layui-tab-content');
				
				var tool = $('.lap-tab-tool')
				, tool_body = $('.lap-tab-tool-body');
			}
		},
		tab_exists: function(id) {
			return this._content.find('.layui-tab-item[lay-item-id=' + id + ']').length > 0;
		},
		add_tab: function(opts) {
			var _self = this
			, cfg = _self._config
			, title = (options = opts ||
					{
						id: -1, 
						title: 'new tab',
						icon: '',
						url: '404.html'
					}).title	
			, icon = options.icon
			, url = options.url
			, id = options.id;
			
			if (_self.tab_exists(id)) {
				//_self.change_tab(id);
				_self.delete_tab(id);
			}
				var r = ['<li class="layui-this" lay-id="' + id + '">'];
				r.push('<i class="layui-icon">' + icon + '</i>')
				, r.push('&nbsp;' + title)
				, r.push('<i class="layui-icon layui-unselect layui-tab-close">&#x1006;</i>')
				, r.push('</li>');
				
				var o = '<div class="layui-tab-item layui-show" lay-item-id="' + id + '"><iframe class="tab-frame" src="' + url +'"></iframe></div>';
				
				_self._title.append(r.join('')) , _self._content.append(o);
				
				_self.get_tab(id).find('i.layui-tab-close')
					.off('click')
					.on('click', function() {
						alert('close');
						_self.delete_tab(id);
					});
				
				_self.change_tab(id);
				_self.resize();
			
				_self._content.find('div[lay-item-id=' + id + ']').find('iframe').on('load', function() {
					$(this).height(win.height()-90);
				});
			//}
		},
		
		delete_tab: function(id) {
			$('.layui-tab-item').filter(function() {
				return $(this).attr('lay-item-id') == id
			}).remove();
		},
		change_tab: function(id) {
			$('.layui-tab-item').removeClass('layui-show').filter(function() {
				return $(this).attr('lay-item-id') == id
			}).addClass('layui-show');
		},
		get_tab: function(id) {
			return this._title.find('li[lay-id="' + id + '"]');
		},
		resize: function() {
			var _self = this;
			win.on('resize', function() {
				var h = $(_self._parent_elem).height();
				//console.log(h)
/*				$('.ping-tab .layui-tab-content iframe').height(h - 50);
*/				//$('.lap-tab .layui-tab-content iframe').height(h);

			//}).resize();
			});
		}
	};
	
	exports('tab', new tab());
});























