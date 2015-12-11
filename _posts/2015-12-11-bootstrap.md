---
layout: post
title:  "Adding bootstrap."
date:   2015-12-11 21:46:39 +0100
author: Ewout
---

I added bootstrap to my blog. Adding bootstrap to Jekyll was real easy. I simply dropped the cdn scripts 
from [bootstrap's getting started page](getbootstrap.com/getting-started/) into the head.html file in the _includes 
folder as you can see [here](https://github.com/DenEwout/denewout.github.io/commit/7b11ef6c15f26cdf3b532bcff20ebe83ab4a3765).

After all that was done, I wanted to have my site use a three column layout, which I applied 
[here](https://github.com/DenEwout/denewout.github.io/commit/92ed68047071fa7c752939ced1b0d611fd2482b0). I had some trouble
with my "about" link colliding the "Fork me on GitHub" banner, which I solved using the Sass media query below:

{% highlight scss %}
    @media screen and (min-width: 768px) and (max-width: 992px) {
        .navbar-right{
            margin-right: 100px;
        }
    }
{% endhighlight %}

I don't know anything about [Sass](http://sass-lang.com/), I'm more used with working [Less](http://lesscss.org/). 
The default Jekyll theme came with sass, so I'm rolling with the punches. Sass seems to be gaining more popularity with 
[Bootstrap 4 dropping Less in favor of Sass](http://blog.getbootstrap.com/2015/08/19/bootstrap-4-alpha/).

I'm a bit *annoyed* that I couldn't use bootstrap variables inside the media query (because I'm using a CDN to load bootstrap), but I'll worry about that later. 

Next up, a proper archive. Because these massively impressive blog posts are stacking up! *#sarcasm*
