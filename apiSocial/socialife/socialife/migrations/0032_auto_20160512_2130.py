# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0031_auto_20160512_1910'),
    ]

    operations = [
        migrations.AlterField(
            model_name='plan',
            name='usuario',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL, unique=True),
        ),
    ]
