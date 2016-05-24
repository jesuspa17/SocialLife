# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('socialife', '0027_auto_20160511_2153'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='plan',
        ),
        migrations.AddField(
            model_name='estaapuntadoa',
            name='plan',
            field=models.ManyToManyField(to='socialife.Plan'),
        ),
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='usuario',
        ),
        migrations.AddField(
            model_name='estaapuntadoa',
            name='usuario',
            field=models.ManyToManyField(to=settings.AUTH_USER_MODEL),
        ),
    ]
