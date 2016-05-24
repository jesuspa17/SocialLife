# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0019_auto_20160511_2045'),
    ]

    operations = [
        migrations.AlterField(
            model_name='estaapuntadoa',
            name='plan',
            field=models.OneToOneField(to='socialife.Plan'),
        ),
        migrations.AlterField(
            model_name='estaapuntadoa',
            name='usuario',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL),
        ),
    ]
