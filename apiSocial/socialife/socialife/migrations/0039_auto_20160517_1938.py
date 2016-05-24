# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0038_auto_20160516_2131'),
    ]

    operations = [
        migrations.AlterField(
            model_name='estaapuntadoa',
            name='usuario',
            field=models.ForeignKey(to='socialife.Usuario'),
        ),
    ]
