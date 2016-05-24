# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0037_auto_20160516_1909'),
    ]

    operations = [
        migrations.AlterField(
            model_name='plan',
            name='usuario',
            field=models.ForeignKey(to='socialife.Usuario'),
        ),
    ]
