# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0033_auto_20160512_2131'),
    ]

    operations = [
        migrations.AlterField(
            model_name='plan',
            name='usuario',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL, unique=True),
        ),
        migrations.AlterField(
            model_name='usuario',
            name='usuario',
            field=models.OneToOneField(to=settings.AUTH_USER_MODEL),
        ),
    ]
