# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0024_remove_estaapuntadoa_plan'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='usuario',
        ),
        migrations.DeleteModel(
            name='EstaApuntadoA',
        ),
    ]
